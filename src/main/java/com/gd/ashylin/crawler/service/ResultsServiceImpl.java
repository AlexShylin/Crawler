package com.gd.ashylin.crawler.service;

import com.gd.ashylin.crawler.db.DAOFactory;
import com.gd.ashylin.crawler.db.dao.ScrapResultDAO;
import com.gd.ashylin.crawler.db.dao.CrawlerDAO;
import com.gd.ashylin.crawler.db.entity.ScrapResult;
import com.gd.ashylin.crawler.db.entity.Crawler;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * @author Alexander Shylin
 */
public class ResultsServiceImpl implements ResultsService {

    DAOFactory daoFactory = DAOFactory.getH2DAOFactory();

    @Override
    public String getResult(long id, long from, long to, String sortingAcsDesc) {
        CrawlerDAO crawlerDAO = daoFactory.getCrawlerDAO();
        Crawler crawler = crawlerDAO.getCrawlerById(id, from, to, sortingAcsDesc);

        String scanJSON = null;
        try {
            scanJSON = new ObjectMapper().writeValueAsString(crawler);
        } catch (IOException e) {
            // TODO logging
        }

        return scanJSON;
    }

    @Override
    public String getStatus(long id) {
        CrawlerDAO crawlerDAO = daoFactory.getCrawlerDAO();
        String status = crawlerDAO.getCrawlerStatus(id);

        return status;
    }

    @Override
    public String getScrapResult(long id, long scanId) {
        ScrapResultDAO scanDAO = daoFactory.getScrapResultDAO();
        ScrapResult scrapResult = scanDAO.getScrapResult(id, scanId);

        String scanJSON = null;
        try {
            scanJSON = new ObjectMapper().writeValueAsString(scrapResult);
        } catch (IOException e) {
            // TODO logging
        }

        return scanJSON;
    }
}
