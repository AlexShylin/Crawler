package com.gd.ashylin.crawler.db.dao;

import com.gd.ashylin.crawler.db.entity.ScrapResult;

/**
 * @author Alexander Shylin
 */
public interface ScrapResultDAO {
    ScrapResult getScrapResult(long id, long idScan);
}
