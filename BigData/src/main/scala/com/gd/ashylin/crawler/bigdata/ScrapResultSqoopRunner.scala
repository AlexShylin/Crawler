package com.gd.ashylin.crawler.bigdata

import java.sql.Timestamp

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

object ScrapResultSqoopRunner {
  def getDataByTimestampLaunch(tmstFrom: Timestamp, tmstTo: Timestamp): Unit = {

  }
}
