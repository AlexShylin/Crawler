package com.gd.ashylin.crawler.db.dao;

import com.gd.ashylin.crawler.db.entity.Result;
import com.gd.ashylin.crawler.db.entity.ScrapResult;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author Alexander Shylin
 */
public interface CrawlerDao {

    /*
     * selects
     */

    Result getCrawlerById(long id, int offset, int pageSize, String sort);

    Result getStatsByUrl(String url);

    String getCrawlerStatus(long id);

    List<ScrapResult> getScrapResult(long crawlerId, String search, String field);


    /*
     * inserts
     */

    long insertCrawler(Result result);

    void insertScraps(List<ScrapResult> scrapResults, long id);


    /*
     * updates
     */

    void changeCrawlerStatusDateFinish(long id, String status, Timestamp finish);

}
