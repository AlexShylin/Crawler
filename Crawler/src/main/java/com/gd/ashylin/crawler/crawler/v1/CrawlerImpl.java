package com.gd.ashylin.crawler.crawler.v1;

import com.gd.ashylin.crawler.crawler.listener.CrawlerFinishListener;
import com.gd.ashylin.crawler.crawler.listener.CrawlerLaunchListener;
import com.gd.ashylin.crawler.crawler.util.PageDataSource;
import com.gd.ashylin.crawler.crawler.util.UrlUtil;
import com.gd.ashylin.crawler.db.entity.Result;
import com.gd.ashylin.crawler.db.entity.ScrapResult;
import com.gd.ashylin.crawler.exception.RelaunchCrawlerException;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;

/**
 * @author Alexander Shylin
 */
public class CrawlerImpl extends Thread implements Crawler {

    static final Logger LOGGER = Logger.getLogger(CrawlerImpl.class);

    // controlling
    private Result result;
    private CrawlerLaunchListener crawlerLaunchListener;
    private CrawlerFinishListener crawlerFinishListener;
    private UrlUtil urlUtil;
    private PageDataSource pageDataSource;
    private boolean canceled;

    private String url;
    private long delay;

    private static final String STATUS_UNDEFINED = "undefined";
    private static final String STATUS_CONNECTION_TIMEOUT = "Connection timeout";
    private static final String STATUS_NOT_HTTP_OR_HTTPS = "Not HTTP or HTTPS binding";
    private static final String STATUS_WRONG_PROTOCOL = "Wrong protocol";
    private static final String STATUS_INVALID_URL = "Invalid url";
    private static final String STATUS_TOO_LONG_URL = "Too long url";
    private static final String HTML_HREF_ATTRIBUTE = "href";
    private static final String SOURCE_URL_ROOT = "/";

    @Override
    public long launch() {
        if (canceled) {
            throw new RelaunchCrawlerException();
        }


        result = new Result(url, 1, delay);

        if (crawlerLaunchListener != null) {
            crawlerLaunchListener.eventDispatcher(result);
        }

        this.start();
        return result.getId();
    }

    @Override
    public void cancel() {
        canceled = true;
        if (result != null) {
            result.setStatus(Result.STATUS_CANCELED);
            result.setTimestampFinish(new Date());
        }
    }

    @Override
    public void run() {
        crawl(result.getUrl(), SOURCE_URL_ROOT);

        if (!result.getStatus().equals(Result.STATUS_CANCELED)) {
            result.setStatus(Result.STATUS_FINISHED);
        }
        result.setTimestampFinish(new Date());
        crawlerFinishListener.eventDispatcher(result);
    }

    void crawl(String url, String sourceUrl) {
        if (canceled) {
            return;
        }

        ScrapResult scrapResult = new ScrapResult(0, url, sourceUrl, STATUS_UNDEFINED, 0);

        try {
            url = urlUtil.convertPathToAbsolute(result.getUrl(), url, true);
        } catch (MalformedURLException ex) {
            scrapResult.setStatus(STATUS_INVALID_URL);
            return;
        } finally {
            scrapResult.setUrl(url);

            if (result.contains(scrapResult)) {
                return;
            } else {
                result.add(scrapResult);
            }
        }

        long start = System.currentTimeMillis();
        String status = pageDataSource.getPageStatusCode(url);
        long dif = System.currentTimeMillis() - start;

        scrapResult.setResponseTime(dif);
        scrapResult.setStatus(status);

        switch (status) {
            case STATUS_TOO_LONG_URL:
                scrapResult.setUrl(url.substring(0, 1530) + "...");
            case STATUS_WRONG_PROTOCOL:
            case STATUS_NOT_HTTP_OR_HTTPS:
            case STATUS_CONNECTION_TIMEOUT:
                return;
        }

        if (urlUtil.isInternalUrl(result.getUrl(), url) && status.equals(HttpStatus.OK.toString()) && !canceled) {
            Document document;
            try {
                document = (Document) pageDataSource.getDocument(url);
            } catch (IOException e) {
                String message = String.format("Error while crawling when getting Document on %s from source %s", url, sourceUrl);
                LOGGER.error(message, e);
                return;
            }

            Elements links = document.select(String.format("a[%s]", HTML_HREF_ATTRIBUTE));

            if (links != null) {
                for (Element link : links) {
                    doSleep(url, sourceUrl);
                    crawl(link.attr(HTML_HREF_ATTRIBUTE), url);
                    if (canceled) {
                        return;
                    }
                }
            }
        }
    }

    private void doSleep(String url, String sourceUrl) {
        try {
            Thread.sleep(result.getDelay());
        } catch (InterruptedException e) {
            String message = String.format("Error while crawling when doing delay on %s from source %s", url, sourceUrl);
            LOGGER.error(message, e);
        }
    }

    @Override
    public boolean isCanceled() {
        return canceled;
    }

    @Override
    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public void setCrawlerLaunchListener(CrawlerLaunchListener crawlerLaunchListener) {
        this.crawlerLaunchListener = crawlerLaunchListener;
    }

    public void setCrawlerFinishListener(CrawlerFinishListener crawlerFinishListener) {
        this.crawlerFinishListener = crawlerFinishListener;
    }

    public void setUrl(String url) {
        this.url = new UrlUtil().removeEndingSlashes(url);
    }

    public void setThreads(int threads) {
        // pass
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public void setUrlUtil(UrlUtil urlUtil) {
        this.urlUtil = urlUtil;
    }

    public void setPageDataSource(PageDataSource pageDataSource) {
        this.pageDataSource = pageDataSource;
    }
}
