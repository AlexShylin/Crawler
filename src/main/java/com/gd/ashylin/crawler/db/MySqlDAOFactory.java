package com.gd.ashylin.crawler.db;

import com.gd.ashylin.crawler.db.dao.DbMetadataDAO;
import com.gd.ashylin.crawler.db.dao.MySqlDbMetadataDAO;

public class MySqlDAOFactory extends DAOFactory {
    public static final String connectionString = "jdbc:mysql://localhost:3306/mysql";
    public static final String user = "root";
    public static final String password = "root";

    public DbMetadataDAO getDbMetadataDAO() {
        return new MySqlDbMetadataDAO();
    }
}
