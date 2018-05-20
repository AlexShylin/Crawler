package com.gd.ashylin.crawler.bigdata

import java.io.File
import java.sql.Timestamp

import org.apache.log4j.{LogManager, Logger}

object UrlStatisticsSparkDataResolver {

  val log: Logger = LogManager.getRootLogger
  val separator = "\t"

  val sqoopRawName = "scrap_data_raw"

  // todo get from properties
  val pathSqoopRawBucket = ""

  val pathToSqoopRawData = s"$pathToSqoopRawData/$sqoopRawName"


  private[bigdata] def filePathSqoopRaw(tmstLaunchFrom: Timestamp, tmstLaunchTo: Timestamp): String = {
    new File(pathToSqoopRawData).getPath
  }

  private[bigdata] def parse(line: String): ScrapResultData = {
    val values: Array[String] = line.split(separator)
    val id = values(0).toLong
    val status = values(1)
    val timestampLaunch = Timestamp.valueOf(values(2))
    val timestampFinish = Timestamp.valueOf(values(3))
    val url = values(4)
    val threads = values(5).toInt
    val delay = values(6).toLong
    val idScrap = values(7).toLong
    val sourceurl = values(8)
    val statusScrap = values(9)
    val responseTime = values(10).toLong
    ScrapResultData(id, status, timestampLaunch, timestampFinish, url, threads, delay,
      idScrap, sourceurl, statusScrap, responseTime)
  }
}
