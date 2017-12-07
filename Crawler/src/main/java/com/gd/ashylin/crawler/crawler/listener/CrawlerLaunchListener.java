package com.gd.ashylin.crawler.crawler.listener;

import com.gd.ashylin.crawler.db.entity.Result;

/**
 * @author Alexander Shylin
 */
public interface CrawlerLaunchListener {
    void eventDispatcher(Result result);
}
