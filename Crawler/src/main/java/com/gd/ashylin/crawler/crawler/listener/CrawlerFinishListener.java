package com.gd.ashylin.crawler.crawler.listener;

import com.gd.ashylin.crawler.db.entity.Result;

/**
 * @author Alexander Shylin
 */
public interface CrawlerFinishListener {
    void eventDispatcher(Result result);
}
