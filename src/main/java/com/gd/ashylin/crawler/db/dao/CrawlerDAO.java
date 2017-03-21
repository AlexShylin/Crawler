package com.gd.ashylin.crawler.db.dao;

import com.gd.ashylin.crawler.db.entity.Crawler;

/**
 * @author Alexander Shylin
 */
public interface CrawlerDAO {
    Crawler getCrawlerById(long id, long from, long to, String sortingAcsDesc);
    String getCrawlerStatus(long id);
    void addCrawler(Crawler crawler);
    void changeCrawlerStatus(long id, String status);
}
