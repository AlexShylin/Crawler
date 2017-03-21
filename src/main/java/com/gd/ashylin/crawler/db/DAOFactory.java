package com.gd.ashylin.crawler.db;

import com.gd.ashylin.crawler.db.dao.ScrapResultDAO;
import com.gd.ashylin.crawler.db.dao.DbMetadataDAO;
import com.gd.ashylin.crawler.db.dao.CrawlerDAO;

/**
 * Oracle DAO implementation
 *
 * @author Alexander Shylin
 */
public abstract class DAOFactory {

    public abstract DbMetadataDAO getDbMetadataDAO();
    public abstract CrawlerDAO getCrawlerDAO();
    public abstract ScrapResultDAO getScrapResultDAO();

    public static DAOFactory getH2DAOFactory() {
        return new H2DAOFactory();
    }

    public static DAOFactory getH2DAOFactory(String connectionString, String user, String password, String driver) {
        return new H2DAOFactory(connectionString, user, password, driver);
    }
}
