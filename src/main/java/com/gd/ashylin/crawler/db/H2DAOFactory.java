package com.gd.ashylin.crawler.db;

import com.gd.ashylin.crawler.db.dao.DbMetadataDAO;
import com.gd.ashylin.crawler.db.dao.H2DbMetadataDAO;

public class H2DAOFactory extends DAOFactory {

    public DbMetadataDAO getDbMetadataDAO() {
        return new H2DbMetadataDAO();
    }
}
