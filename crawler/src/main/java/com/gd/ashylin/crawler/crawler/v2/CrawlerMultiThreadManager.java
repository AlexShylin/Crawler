package com.gd.ashylin.crawler.crawler.v2;

import com.gd.ashylin.crawler.crawler.listener.CrawlerFinishListener;
import com.gd.ashylin.crawler.crawler.listener.CrawlerLaunchListener;
import com.gd.ashylin.crawler.db.entity.Result;

/**
 * @author Alexander Shylin
 */
public interface CrawlerMultiThreadManager extends Runnable {
    long launch();

    void cancel();

    Result getResult();

    void setCrawlerLaunchListener(CrawlerLaunchListener crawlerLaunchListener);

    void setCrawlerFinishListener(CrawlerFinishListener crawlerFinishListener);

    void setUrl(String url);

    void setThreads(int threads);

    void setDelay(long delay);

    void setEnableCaching(Boolean enableCaching);
}
