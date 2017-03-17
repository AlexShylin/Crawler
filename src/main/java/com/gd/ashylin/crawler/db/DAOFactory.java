package com.gd.ashylin.crawler.db;

import com.gd.ashylin.crawler.db.dao.DbMetadataDAO;

/**
 * Oracle DAO implementation
 */
public abstract class DAOFactory {

    public abstract DbMetadataDAO getDbMetadataDAO();


    public static DAOFactory getH2DAOFactory() {
        return new H2DAOFactory();
    }

    public static DAOFactory getH2DAOFactory(String connectionString, String user, String password, String driver) {
        return new H2DAOFactory(connectionString, user, password, driver);
    }
}
