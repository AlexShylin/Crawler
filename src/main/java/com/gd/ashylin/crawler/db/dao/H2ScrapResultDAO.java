package com.gd.ashylin.crawler.db.dao;

import com.gd.ashylin.crawler.db.entity.ScrapResult;

import javax.sql.DataSource;

/**
 * @author Alexander Shylin
 */
public class H2ScrapResultDAO extends ConnectionControl implements ScrapResultDAO {

    public H2ScrapResultDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /*
     * Implemented methods
     */

    @Override
    public ScrapResult getScrapResult(long id, long idScan) {
        return null;
    }
}
