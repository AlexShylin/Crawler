package com.gd.ashylin.crawler.service.v1;

import com.gd.ashylin.crawler.crawler.listener.CrawlerFinishListener;
import com.gd.ashylin.crawler.crawler.v1.Crawler;
import com.gd.ashylin.crawler.db.dao.CrawlerDao;
import com.gd.ashylin.crawler.db.entity.Result;
import com.gd.ashylin.crawler.dto.*;
import com.gd.ashylin.crawler.service.validator.RequestValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.ObjectFactory;

import java.util.Map;

/**
 * @author Alexander Shylin
 */
public class CrawlersServiceV1 implements CrawlersService {

    private RequestValidator requestValidator;
    private Map<Long, Crawler> processingCrawlers;
    private CrawlerDao crawlerDAO;
    private ObjectFactory<Crawler> crawlerFactory;

    static final Logger LOGGER = Logger.getLogger(CrawlersServiceV1.class);

    private static final long TIME_TO_SLEEP = 1000L;

    @Override
    public LaunchDto launch(String url, Long delay) {
        requestValidator.validateLaunchParams(url, delay);

        Crawler crawler = crawlerFactory.getObject();
        crawler.setDelay(delay);
        crawler.setUrl(url);
        crawler.setCrawlerLaunchListener(result -> result.setId(crawlerDAO.insertCrawler(result)));
        crawler.setCrawlerFinishListener(new CrawlerFinishListener() {
            @Override
            public void eventDispatcher(Result result) {
                updateCrawlerFinish(result);
                insertScraps(result);
                processingCrawlers.remove(result.getId());
            }

            private void insertScraps(Result result) {
                crawlerDAO.insertScraps(result.getResults(), result.getId());
            }

            private void updateCrawlerFinish(Result result) {
                crawlerDAO.changeCrawlerStatusDateFinish(result.getId(), result.getStatus(), result.getTimestampFinish());
            }
        });

        LaunchDto responseBinding = new LaunchDto(crawler.launch());
        processingCrawlers.put(responseBinding.getId(), crawler);
        return responseBinding;
    }

    @Override
    public CancelDto cancel(Long id) {
        requestValidator.validateId(id);

        Crawler crawler;
        if (processingCrawlers.containsKey(id)) {
            crawler = processingCrawlers.get(id);
            crawler.cancel();
            return new CancelDto(crawler.getResult().getStatus());
        } else {
            return new CancelDto(getStatus(id).getStatus());
        }
    }

    @Override
    public ResultsDto getResult(Long id, String sort, Integer offset, Integer pageSize) {
        requestValidator.validateGetResultParams(id, sort, offset, pageSize);

        while (processingCrawlers.containsKey(id)) {
            doSleep();
        }
        Result result = crawlerDAO.getCrawlerById(id, offset, pageSize, sort);
        return new ResultsDto(result);
    }

    @Override
    public StatusDto getStatus(Long id) {
        requestValidator.validateId(id);
        return new StatusDto(crawlerDAO.getCrawlerStatus(id));
    }

    @Override
    public ScrapDto getScrapResults(Long id, String search, String field) {
        while (processingCrawlers.containsKey(id)) {
            doSleep();
        }
        requestValidator.validateGetScrapResultsParams(id, search, field);
        return new ScrapDto(crawlerDAO.getScrapResult(id, search, field));
    }

    public void setRequestValidator(RequestValidator requestValidator) {
        this.requestValidator = requestValidator;
    }

    public void setProcessingCrawlers(Map<Long, Crawler> processingCrawlers) {
        this.processingCrawlers = processingCrawlers;
    }

    public void setCrawlerDAO(CrawlerDao crawlerDAO) {
        this.crawlerDAO = crawlerDAO;
    }

    public void setCrawlerFactory(ObjectFactory<Crawler> crawlerFactory) {
        this.crawlerFactory = crawlerFactory;
    }

    private void doSleep() {
        try {
            Thread.sleep(TIME_TO_SLEEP);
        } catch (InterruptedException e) {
            LOGGER.error("Error while sleep in Crawlers Service v2", e);
        }
    }
}
