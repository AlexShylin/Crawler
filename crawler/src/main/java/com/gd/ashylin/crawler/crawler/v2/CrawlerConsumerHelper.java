package com.gd.ashylin.crawler.crawler.v2;

import com.gd.ashylin.crawler.db.entity.ScrapResult;

/**
 * Something like listener - allows crawler to add tasks
 * directly to private tasks queue in this thread manager
 * @author Alexander Shylin
 */
public interface CrawlerConsumerHelper {
    void addTask(ScrapResult scrapResult);
}
