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

case class AvgTimeResultEntity(id: Long,
                               status: String,
                               timestampLaunch: Timestamp,
                               timestampFinish: Timestamp,
                               url: String,
                               threads: Int,
                               delay: Long) {
  val STATUS_PROCESSING = "processing"
  val STATUS_CANCELED = "canceled"
  val STATUS_FINISHED = "finished"
}

object UrlStatisticsSpark {

  private type Milliseconds = Long

  private val master = "local[*]"
  private val appName = "urlstat"

  lazy val conf: SparkConf = new SparkConf().setMaster(master).setAppName(appName)
  lazy val sc = new SparkContext(conf)


  //avg response time
  //status count
  //foreign and native url percentage

  // TODO javadoc
  /**
    * time exec
    *
    * @param tmstLaunchFrom launch timestamp start inclusive
    * @param tmstLaunchTo   launch timestamp end exclusive
    */
  def execJobExecutionTime(tmstLaunchFrom: Timestamp, tmstLaunchTo: Timestamp): Unit = {
    def subtractTimestamp(scrRes: ScrapResultData): Milliseconds = {
      scrRes.timestampFinish.getTime - scrRes.timestampLaunch.getTime
    }

    // add request to db
    ScrapResultSqoopRunner.getDataByTimestampLaunch(tmstLaunchFrom, tmstLaunchTo)

    val rdd: RDD[ScrapResultData] = sc.
      textFile(UrlStatisticsSparkDataResolver.filePath + s"_${tmstLaunchFrom}_${tmstLaunchTo}").
      map(UrlStatisticsSparkDataResolver.parse)

    // TODO partitioner
    val filteredGroupedByUrlsData = rdd.
      filter(s => {
        s.timestampLaunch.compareTo(tmstLaunchFrom) >= 0 && s.timestampLaunch.compareTo(tmstLaunchTo) < 0
      }).
      map(s => s.url -> s).
      groupByKey()

    // TODO save to file
    val avgByUrls = filteredGroupedByUrlsData.
      aggregateByKey((0L, 0))(
        (u, iter) => {
          (u._1 + iter.map(subtractTimestamp).sum, iter.size)
        },
        (tup1, tup2) => (tup1._1 + tup2._1, tup1._2 + tup2._2)
      ).mapValues(x => x._1 / x._2)

    /*
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

    // max


    // min
  }

}
