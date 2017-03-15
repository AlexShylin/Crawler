package com.gd.ashylin.crawler.db;

import com.gd.ashylin.crawler.db.entity.DbMetadataDAO;

/**
 * Oracle DAO implementation
 */
public abstract class DAOFactory {
    // db indexes
    public static final int MYSQL = 1;

    public abstract DbMetadataDAO getDbMetadata();

    public static DAOFactory getDAOFactory(int dbIndex) {
        switch (dbIndex) {
            case 1:
                return new MySqlDAOFactory();
            default:
                return null;
        }
    }
}
