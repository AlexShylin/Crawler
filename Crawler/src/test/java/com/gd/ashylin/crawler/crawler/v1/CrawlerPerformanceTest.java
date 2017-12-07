package com.gd.ashylin.crawler.crawler.v1;

import com.gd.ashylin.crawler.crawler.util.JsoupDataSource;
import com.gd.ashylin.crawler.crawler.util.UrlUtil;
import com.gd.ashylin.crawler.db.entity.Result;
import com.gd.ashylin.crawler.marker.SlowPerformanceTest;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.concurrent.TimeUnit;

/**
 * ATTENTION!
 * These tests may proceed very long time.
 * That's why default marked with @Category(SlowPerformanceTest.class).
 * <p>
 * Category(class) marker allows to ignore all marked tests
 * without @Ignore annotation.
 * <p>
 * Designed for crawler's performance testing, debugging
 * and comparing with another versions and
 * implementations of crawler.
 *
 * @author Alexander Shylin
 */
@SuppressWarnings("Duplicates")
@Category(SlowPerformanceTest.class)
public class CrawlerPerformanceTest {
    private static final String URL_1 = "http://www.nashpartnership.com";
    private static final String URL_2 = "http://kbroman.org";

    private static final Long DELAY = 0L;

    static final Logger LOGGER = Logger.getLogger(CrawlerPerformanceTest.class);

    @Test
    public void testCrawler() throws InterruptedException {
        String url = URL_1;

        LOGGER.info("Started performance test Crawler v1 on " + url);

        long start = System.currentTimeMillis();
        CrawlerImpl mm = new CrawlerImpl();
        mm.setUrlUtil(new UrlUtil());
        mm.setPageDataSource(new JsoupDataSource());
        mm.setUrl(url);
        mm.setDelay(DELAY);
        mm.setCrawlerLaunchListener(result -> {
        });
        mm.setCrawlerFinishListener(result -> {
        });
        mm.launch();
        mm.join();
        Result result = mm.getResult();
        long finish = System.currentTimeMillis();
        long duration = finish - start;
        String durationString = String.format("; duration %d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes(duration),
                TimeUnit.MILLISECONDS.toSeconds(duration) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration))
        );
        String elementsCount = String.format("; links found %d", result.getResults().size());

        LOGGER.info("Finished performance test Crawler v1 on " + url + durationString + elementsCount);
    }

    @Test
    public void testCrawler2() throws InterruptedException {
        String url = URL_2;

        LOGGER.info("Started performance test Crawler v1 on " + url);

        long start = System.currentTimeMillis();
        CrawlerImpl mm = new CrawlerImpl();
        mm.setUrlUtil(new UrlUtil());
        mm.setPageDataSource(new JsoupDataSource());
        mm.setUrl(url);
        mm.setDelay(DELAY);
        mm.setCrawlerLaunchListener(result -> {
        });
        mm.setCrawlerFinishListener(result -> {
        });
        mm.launch();
        mm.join();
        Result result = mm.getResult();
        long finish = System.currentTimeMillis();
        long duration = finish - start;
        String durationString = String.format("; duration %d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes(duration),
                TimeUnit.MILLISECONDS.toSeconds(duration) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration))
        );
        String elementsCount = String.format("; links found %d", result.getResults().size());

        LOGGER.info("Finished performance test Crawler v1 on " + url + durationString + elementsCount);
    }
}
