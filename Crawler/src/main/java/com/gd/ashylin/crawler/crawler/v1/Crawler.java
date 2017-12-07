package com.gd.ashylin.crawler.crawler.v1;

import com.gd.ashylin.crawler.crawler.listener.CrawlerFinishListener;
import com.gd.ashylin.crawler.crawler.listener.CrawlerLaunchListener;
import com.gd.ashylin.crawler.crawler.util.PageDataSource;
import com.gd.ashylin.crawler.crawler.util.UrlUtil;
import com.gd.ashylin.crawler.db.entity.Result;

/**
 * @author Alexander Shylin
 */
public interface Crawler {

    long launch();

    void cancel();

    boolean isCanceled();

    Result getResult();

    void setResult(Result result);

    void setCrawlerLaunchListener(CrawlerLaunchListener crawlerLaunchListener);

    void setCrawlerFinishListener(CrawlerFinishListener crawlerFinishListener);

    void setUrl(String url);

    void setThreads(int threads);

    void setDelay(long delay);

    void setUrlUtil(UrlUtil urlUtil);

    void setPageDataSource(PageDataSource pageDataSource);
}
