package com.gd.ashylin.crawler.service.v2;

import com.gd.ashylin.crawler.dto.ResultsDto;
import com.gd.ashylin.crawler.dto.ScrapDto;
import com.gd.ashylin.crawler.dto.StatusDto;
import com.gd.ashylin.crawler.crawler.v2.CrawlerMultiThreadManager;
import com.gd.ashylin.crawler.db.dao.CrawlerDao;
import com.gd.ashylin.crawler.db.entity.Result;
import com.gd.ashylin.crawler.db.entity.ScrapResult;
import com.gd.ashylin.crawler.service.validator.RequestValidator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.ObjectFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import static org.mockito.Mockito.*;

/**
 * @author Alexander Shylin
 */
@RunWith(MockitoJUnitRunner.class)
public class CrawlersServiceV2Test {
    @InjectMocks
    private CrawlersServiceV2 crawlersService = new CrawlersServiceV2();

    @Mock
    private ObjectFactory crawlerFactory = mock(ObjectFactory.class);

    @Mock
    private RequestValidator requestValidator = mock(RequestValidator.class);

    @Mock
    private ConcurrentMap processingCrawlers = mock(ConcurrentMap.class);

    @Mock
    private CrawlerDao crawlerDao = mock(CrawlerDao.class);

    private CrawlerMultiThreadManager crawler = mock(CrawlerMultiThreadManager.class);
    private Result result = mock(Result.class);

    @Test
    public void testLaunch() {
        String url = "url";
        Long delay = 0L;
        Long launchReturns = 1L;
        Integer threads = 10;

        doNothing().when(requestValidator).validateLaunchParams(url, threads, delay);
        when(crawlerFactory.getObject()).thenReturn(crawler);
        when(crawler.launch()).thenReturn(launchReturns);
        when(processingCrawlers.put(launchReturns, crawler)).thenReturn(null);

        Long returnedId = crawlersService.launch(url, threads, delay, false).getId();

        // validate order call
        InOrder inOrder = inOrder(crawlerFactory, requestValidator, processingCrawlers, crawler);
        inOrder.verify(requestValidator).validateLaunchParams(url, threads, delay);
        inOrder.verify(crawlerFactory).getObject();
        inOrder.verify(crawler).launch();
        inOrder.verify(processingCrawlers).put(launchReturns, crawler);

        Assert.assertEquals(launchReturns.longValue(), returnedId.longValue());
    }

    @Test
    public void testCancel() {
        Long id = 0L;
        String statusToReturn = "status";

        doNothing().when(requestValidator).validateId(id);
        when(processingCrawlers.containsKey(id)).thenReturn(true);
        when(processingCrawlers.get(id)).thenReturn(crawler);
        doNothing().when(crawler).cancel();
        when(crawler.getResult()).thenReturn(result);
        when(result.getStatus()).thenReturn(statusToReturn);

        String returnedStatus = crawlersService.cancel(id).getStatus();

        InOrder inOrder = inOrder(requestValidator, processingCrawlers, crawler);
        inOrder.verify(requestValidator).validateId(id);
        inOrder.verify(processingCrawlers).containsKey(id);
        inOrder.verify(processingCrawlers).get(id);
        inOrder.verify(crawler).cancel();

        Assert.assertEquals(statusToReturn, returnedStatus);
    }

    @Test
    public void testGetResult() {
        Long id = 0L;
        String sort = "sort";
        Integer offset = 0;
        Integer pageSize = 0;

        doNothing().when(requestValidator).validateGetResultParams(id, sort, offset, pageSize);
        when(processingCrawlers.containsKey(id)).thenReturn(false);
        when(crawlerDao.getCrawlerById(id, offset, pageSize, sort)).thenReturn(result);
        when(result.getId()).thenReturn(id);

        ResultsDto binding = crawlersService.getResult(id, sort, offset, pageSize);

        InOrder inOrder = inOrder(requestValidator, processingCrawlers, crawlerDao);
        inOrder.verify(requestValidator).validateGetResultParams(id, sort, offset, pageSize);
        inOrder.verify(processingCrawlers).containsKey(id);
        inOrder.verify(crawlerDao).getCrawlerById(id, offset, pageSize, sort);

        Assert.assertEquals(id.longValue(), binding.getId());
    }

    @Test
    public void testGetStatus() {
        Long id = 0L;
        String statusToReturn = "status";

        doNothing().when(requestValidator).validateId(id);
        when(crawlerDao.getCrawlerStatus(id)).thenReturn(statusToReturn);

        StatusDto binding = crawlersService.getStatus(id);

        InOrder inOrder = inOrder(requestValidator, crawlerDao);
        inOrder.verify(requestValidator).validateId(id);
        inOrder.verify(crawlerDao).getCrawlerStatus(id);

        Assert.assertEquals(statusToReturn, binding.getStatus());
    }

    @Test
    public void testGetScrapResults() {
        Long id = 0L;
        String search = "search";
        String field = "field";

        List<ScrapResult> scrapResults = new ArrayList<>();
        scrapResults.add(null);

        when(processingCrawlers.containsKey(id)).thenReturn(false);
        doNothing().when(requestValidator).validateGetScrapResultsParams(id, search, field);
        when(crawlerDao.getScrapResult(id, search, field)).thenReturn(scrapResults);

        ScrapDto binding = crawlersService.getScrapResults(id, search, field);

        InOrder inOrder = inOrder(requestValidator, crawlerDao, processingCrawlers);
        inOrder.verify(processingCrawlers).containsKey(id);
        inOrder.verify(requestValidator).validateGetScrapResultsParams(id, search, field);
        inOrder.verify(crawlerDao).getScrapResult(id, search, field);

        Assert.assertEquals(scrapResults, binding.getScrapResults());
    }

}
