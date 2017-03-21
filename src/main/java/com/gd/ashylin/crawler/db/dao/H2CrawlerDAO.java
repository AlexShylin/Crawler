package com.gd.ashylin.crawler.db.dao;

import com.gd.ashylin.crawler.db.entity.Crawler;

import javax.sql.DataSource;

/**
 * @author Alexander Shylin
 */
public class H2CrawlerDAO extends ConnectionControl implements CrawlerDAO {

    public H2CrawlerDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /*
     * Implemented methods
     */

    @Override
    public Crawler getCrawlerById(long id, long from, long to, String sortingAcsDesc) {
        return null;
    }

    @Override
    public String getCrawlerStatus(long id) {
        return null;
    }

    @Override
    public void addCrawler(Crawler crawler) {

    }

    @Override
    public void changeCrawlerStatus(long id, String status) {

    }
}
