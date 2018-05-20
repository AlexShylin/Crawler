package com.gd.ashylin.crawler.bigdata

import java.sql.Timestamp
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


case class ScrapResultData(id: Long,
                           status: String,
                           timestampLaunch: Timestamp,
                           timestampFinish: Timestamp,
                           url: String,
                           threads: Int,
                           delay: Long,
                           idScrap: Long,
                           sourceUrl: String,
                           statusScrap: String,
                           responseTime: Long) {}

case class JobExecTimeResultEntity(id: Long,
                                   status: String,
                                   timestampLaunch: Timestamp,
                                   timestampFinish: Timestamp,
                                   url: String,
                                   threads: Int,
                                   delay: Long,
                                   avgExecTime: Long,
                                   maxExecTime: Long,
                                   minExecTime: Long,
                                   sumExecTime: BigInt) {
  val STATUS_PROCESSING = "processing"
  val STATUS_CANCELED = "canceled"
  val STATUS_FINISHED = "finished"
}

object UrlStatisticsSpark {

  private type Url = String
  private type Milliseconds = Long
  private type SumMillis = Milliseconds
  private type AvgMillis = Milliseconds
  private type MaxMillis = Milliseconds
  private type MinMillis = Milliseconds

  private val master = "local[*]"
  private val appName = "urlstat"

  lazy val conf: SparkConf = new SparkConf().setMaster(master).setAppName(appName)
  lazy val sc = new SparkContext(conf)


  //avg response time
  //status count
  //foreign and native url percentage

  /**
    * time exec
    *
    * @param tmstLaunchFrom launch timestamp start inclusive
    * @param tmstLaunchTo   launch timestamp end exclusive
    */
  def execJobExecutionTime(tmstLaunchFrom: Timestamp,
                           tmstLaunchTo: Timestamp,
                           forceRecomputeExecTime: Boolean): Map[Url, JobExecTimeResultEntity] = {

    /**
      * Produces difference between timestamps in milliseconds
      *
      * @param scrRes scrap result object
      * @return difference in milliseconds
      */
    def subtractTimestamp(scrRes: ScrapResultData): Milliseconds = {
      scrRes.timestampFinish.getTime - scrRes.timestampLaunch.getTime
    }

    val groupedByUrlsData: RDD[(Url, Iterable[ScrapResultData])] =
      getPartitionedByUrlsRDD(tmstLaunchFrom, tmstLaunchTo)

    // (url, (sum, avg))
    val avgByUrls: RDD[(Url, (SumMillis, AvgMillis))] = groupedByUrlsData.
      aggregateByKey((0L, 0))(
        (u, iter) => {
          (u._1 + iter.map(subtractTimestamp).sum, iter.size)
        },
        (tup1, tup2) => (tup1._1 + tup2._1, tup1._2 + tup2._2)
      ).mapValues(x => (x._1, x._1 / x._2))

    /* Proof that parallel avg works as right as single thread avg
     *
     * 13 \
     *      28/2=14  \
     * 15 /           \
     *                  75/2 -> 37.5
     * 78 \           /
     *      122/2=61 /
     * 44 /
     *
     * ------------------------
     *
     * 13 \
     * 15  \
     *       150/4=37.5
     * 78  /
     * 44 /
     *
     */

    // max and max
    val minMaxByUrls: RDD[(Url, (MinMillis, MaxMillis))] = groupedByUrlsData.mapValues(sr => {
      val milliseconds = sr.map(subtractTimestamp)
      (milliseconds.max, milliseconds.min)
    })

    val joinedResultRdd: RDD[(Url, (ScrapResultData, SumMillis, AvgMillis, MinMillis, MaxMillis))] =
      groupedByUrlsData.
        join(avgByUrls.join(minMaxByUrls)).
        map(joined => {
          joined._1 ->
            (joined._2._1.head, joined._2._2._1._1, joined._2._2._1._2, joined._2._2._2._1, joined._2._2._2._2)
        })

    val result = joinedResultRdd.
      mapValues(v => {
        val scrRes = v._1
        JobExecTimeResultEntity(
          scrRes.id,
          scrRes.status,
          scrRes.timestampLaunch,
          scrRes.timestampFinish,
          scrRes.url,
          scrRes.threads,
          scrRes.delay,
          v._3,
          v._5,
          v._4,
          v._2
        )
      })

    result.collect().toMap
  }

  // todo min, max, avg, sum response time

  private def getPartitionedByUrlsRDD(tmstLaunchFrom: Timestamp,
                              tmstLaunchTo: Timestamp): RDD[(String, Iterable[ScrapResultData])] = {
    // TODO add sqoop request to db
    ScrapResultSqoopRunner.getDataByTimestampLaunch(tmstLaunchFrom, tmstLaunchTo)

    val rdd: RDD[ScrapResultData] = sc.
      textFile(UrlStatisticsSparkDataResolver.filePathSqoopRaw(tmstLaunchFrom, tmstLaunchTo)).
      map(UrlStatisticsSparkDataResolver.parse)

    rdd.
      map(s => s.url -> s).
      groupByKey
  }

}
