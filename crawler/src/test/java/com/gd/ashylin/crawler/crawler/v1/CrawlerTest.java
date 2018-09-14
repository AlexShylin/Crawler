package com.gd.ashylin.crawler.crawler.v1;

import com.gd.ashylin.crawler.crawler.util.PageDataSource;
import com.gd.ashylin.crawler.crawler.util.UrlUtil;
import com.gd.ashylin.crawler.db.entity.Result;
import com.gd.ashylin.crawler.exception.RelaunchCrawlerException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;

import static org.mockito.Mockito.*;

/**
 * @author Alexander Shylin
 */
@RunWith(MockitoJUnitRunner.class)
public class CrawlerTest {

    @InjectMocks
    private CrawlerImpl crawler = new CrawlerImpl();

    @Mock
    private UrlUtil urlUtil = mock(UrlUtil.class);

    @Mock
    private PageDataSource pageDataSource = mock(PageDataSource.class);

    @Test(expected = RelaunchCrawlerException.class)
    public void testLaunchCanceled() {
        crawler.cancel();
        crawler.launch();
    }

    @Test
    public void testCancel() {
        crawler.cancel();
        Assert.assertTrue(crawler.isCanceled());
    }

    @Test
    public void testCrawlRightCallOrder() throws IOException {
        /*
         * fields
         */
        String url = "./src/test/resources/test.html";
        String hrefGoogle = "https://google.com";
        String hrefYoutube = "https://youtube.com";
        String domain = "";
        Document doc = Jsoup.parse(new File(url), "UTF-8");


        /*
         * behavior
         */
        when(urlUtil.convertPathToAbsolute(domain, url, true)).thenReturn(url);
        when(urlUtil.convertPathToAbsolute(domain, hrefGoogle, true)).thenReturn(hrefGoogle);
        when(urlUtil.convertPathToAbsolute(domain, hrefYoutube, true)).thenReturn(hrefYoutube);

        when(pageDataSource.getPageStatusCode(any(String.class))).thenReturn("200");

        when(pageDataSource.getDocument(url)).thenReturn(doc);
        when(pageDataSource.getDocument(hrefGoogle)).thenReturn(null);
        when(pageDataSource.getDocument(hrefYoutube)).thenReturn(null);

        when(urlUtil.isInternalUrl(domain, url)).thenReturn(true);
        when(urlUtil.isInternalUrl(domain, hrefGoogle)).thenReturn(false);
        when(urlUtil.isInternalUrl(domain, hrefYoutube)).thenReturn(false);


        /*
         * call
         */
        crawler.setResult(new Result(domain, 1, 1L));
        crawler.crawl(url, domain);

        /*
         * check order
         */
        InOrder inOrder = inOrder(urlUtil, pageDataSource);
        inOrder.verify(urlUtil).convertPathToAbsolute(domain, url, true);
        inOrder.verify(pageDataSource).getPageStatusCode(url);
        inOrder.verify(urlUtil).isInternalUrl(domain, url);
        inOrder.verify(pageDataSource).getDocument(url);

        Assert.assertEquals(3, crawler.getResult().getResults().size());
    }
}
