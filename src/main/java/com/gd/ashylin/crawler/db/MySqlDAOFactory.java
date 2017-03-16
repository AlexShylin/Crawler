package com.gd.ashylin.crawler.db;

import com.gd.ashylin.crawler.db.dao.DbMetadataDAO;
import com.gd.ashylin.crawler.db.dao.MySqlDbMetadataDAO;

public class MySqlDAOFactory extends DAOFactory {
    public DbMetadataDAO getDbMetadataDAO() {
        return new MySqlDbMetadataDAO();
    }
}
