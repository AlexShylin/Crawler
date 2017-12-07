package com.gd.ashylin.crawler.crawler.v2;

import com.gd.ashylin.cache.accessor.Cache;
import com.gd.ashylin.crawler.crawler.util.JsoupDataSource;
import com.gd.ashylin.crawler.crawler.util.PageDataSource;
import com.gd.ashylin.crawler.crawler.util.UrlUtil;
import com.gd.ashylin.crawler.db.entity.ScrapResult;
import com.gd.ashylin.crawler.dto.CacheDto;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * A part of producer-consumer pattern
 * <p>
 * Consumer - completes tasks from task queue and puts
 * results into Producer {@link com.gd.ashylin.crawler.crawler.v2.CrawlerMultiThreadManagerImpl}
 *
 * @author Alexander Shylin
 */
public class CrawlerImpl implements Crawler {

    static final Logger LOGGER = Logger.getLogger(CrawlerImpl.class);

    private UrlUtil urlUtil = new UrlUtil();
    private PageDataSource pageDataSource = new JsoupDataSource();

    private CrawlerConsumerHelper crawlerConsumer;

    private ScrapResult scrapResult;
    private String rootUrl;

    private Cache<String, CacheDto> cache;

    private Boolean enableCaching;

    static final String STATUS_UNDEFINED = "undefined";
    private static final String STATUS_CONNECTION_TIMEOUT = "Connection timeout";
    private static final String STATUS_NOT_HTTP_OR_HTTPS = "Not HTTP or HTTPS binding";
    private static final String STATUS_WRONG_PROTOCOL = "Wrong protocol";
    private static final String STATUS_INVALID_URL = "Invalid url";
    private static final String STATUS_TOO_LONG_URL = "Too long url";
    private static final String HTML_HREF_ATTRIBUTE = "href";

    CrawlerImpl(ScrapResult scrapResult,
                String rootUrl,
                CrawlerConsumerHelper crawlerConsumer,
                Cache<String, CacheDto> cache,
                Boolean enableCaching) {
        this.scrapResult = scrapResult;
        this.rootUrl = rootUrl;
        this.crawlerConsumer = crawlerConsumer;
        this.cache = cache;
        this.enableCaching = enableCaching;
    }

    @Override
    public void run() {
        String url = scrapResult.getUrl();
        String sourceUrl = scrapResult.getSourceurl();

        if (scrapResult.getStatus().equals(STATUS_INVALID_URL)) {
            return;
        }


        String status;
        Long dif;

        if (enableCaching != null && enableCaching) {
            CacheDto cacheDto = cache.getIfContains(url);

            if (cacheDto == null) {
                long start = System.currentTimeMillis();
                status = pageDataSource.getPageStatusCode(url);
                dif = System.currentTimeMillis() - start;

                cacheDto = new CacheDto();
                cacheDto.setStatus(status);
                cacheDto.setResponseTime(dif);

                cache.put(url, cacheDto);
            } else {
                status = cacheDto.getStatus();
                dif = cacheDto.getResponseTime();
            }
        } else {
            long start = System.currentTimeMillis();
            status = pageDataSource.getPageStatusCode(url);
            dif = System.currentTimeMillis() - start;
        }

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

        if (urlUtil.isInternalUrl(rootUrl, url) && status.equals(HttpStatus.OK.toString())) {
            Document document;
            try {
                document = (Document) pageDataSource.getDocument(url);
            } catch (IOException e) {
                String message = String.format("Error while crawling when getting Document on %s from source %s", url, sourceUrl);
                LOGGER.error(message, e);
                return;
            }

            Elements elements = document.select(String.format("a[%s]", HTML_HREF_ATTRIBUTE));

            if (elements != null) {
                for (Element element : elements) {
                    ScrapResult toAdd = new ScrapResult(0, null, url, STATUS_UNDEFINED, 0);

                    String link = element.attr(HTML_HREF_ATTRIBUTE);

                    try {
                        link = urlUtil.convertPathToAbsolute(rootUrl, link, true);
                    } catch (MalformedURLException e) {
                        toAdd.setStatus(STATUS_INVALID_URL);
                    } finally {
                        toAdd.setUrl(link);
                    }

                    LOGGER.debug(Thread.currentThread().getName());
                    crawlerConsumer.addTask(toAdd);
                }
            }
        }
    }

    @Override
    public void runInCurrentThread() {
        run();
    }

}
