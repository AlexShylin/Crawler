package com.gd.ashylin.crawler.db.dao;

import com.gd.ashylin.crawler.db.MySqlDAOFactory;
import com.gd.ashylin.crawler.db.entity.DbMetadata;
import com.gd.ashylin.crawler.db.entity.Details;
import com.gd.ashylin.crawler.db.entity.Summary;

import java.sql.*;

public class MySqlDbMetadataDAO implements DbMetadataDAO {
    private Connection connection;
    private DatabaseMetaData databaseMetaData;

    // String resources
    private static final String STATUS_OK = "OK";
    private static final String STATUS_ERROR = "ERROR";

    private static final String MESSAGE_HEALTH_PASSED = "All health checks passed";
    private static final String MESSAGE_HEALTH_DB_FAIL = "Database health check failed";

    private static final String MESSAGE_DB_AVAILABLE = "DB available";
    private static final String MESSAGE_DB_UNAVAILABLE = "DB unavailable";

    private static final String LABEL = "MySQL database";

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // TODO logging
        }
    }

    public DbMetadata getDbMetadata() {
        DbMetadata meta = null;
        Summary summary = null;
        Details details = null;

        try {
            connection = DriverManager.getConnection(MySqlDAOFactory.connectionString, MySqlDAOFactory.user, MySqlDAOFactory.password);
            databaseMetaData = connection.getMetaData();

            String productName    = databaseMetaData.getDatabaseProductName();
            String productVersion = databaseMetaData.getDatabaseProductVersion();

            // if no exception, everything is ok
            summary = new Summary(STATUS_OK, MESSAGE_HEALTH_PASSED);
            details = new Details(STATUS_OK, MESSAGE_DB_AVAILABLE, LABEL, productName, productVersion);
            meta = new DbMetadata(summary, details);
        } catch (SQLException e) {
            e.printStackTrace();
            summary = new Summary(STATUS_ERROR, MESSAGE_HEALTH_DB_FAIL);
            details = new Details(STATUS_ERROR, MESSAGE_DB_UNAVAILABLE, LABEL, null, null);
            meta = new DbMetadata(summary, details);
        } finally {
            closeResources();
        }

        return meta;
    }

    private void closeResources() {
        try {
            connection.close();
        } catch (SQLException e) {
            // TODO logging
        }
    }
}
