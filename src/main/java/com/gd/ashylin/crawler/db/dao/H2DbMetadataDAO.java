package com.gd.ashylin.crawler.db.dao;

import com.gd.ashylin.crawler.db.entity.DbMetadata;
import com.gd.ashylin.crawler.db.entity.Details;
import com.gd.ashylin.crawler.db.entity.Summary;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class H2DbMetadataDAO implements DbMetadataDAO {

    private Connection connection;
    private DatabaseMetaData databaseMetaData;

    // String resources
    private static final String LABEL = "H2 database";

    // Connection credentials
    String connectionString;
    String user;
    String password;
    Properties properties;

    static {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            // TODO logging
        }
    }

    {
        properties = new Properties();
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("h2ConnectionCredentials.properties").getFile());
            properties.load(new FileInputStream(file));

            // loading properties
            connectionString = properties.getProperty("connection");
            user = properties.getProperty("user");
            password = properties.getProperty("password");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public DbMetadata getDbMetadata() {
        DbMetadata meta;
        Summary summary;
        Details details;

        try {
            connection = DriverManager.getConnection(connectionString, user, password);
            databaseMetaData = connection.getMetaData();

            String productName = databaseMetaData.getDatabaseProductName();
            String productVersion = databaseMetaData.getDatabaseProductVersion();

            // if no exception, everything is ok
            summary = new Summary(STATUS_OK, MESSAGE_HEALTH_PASSED);
            details = new Details(STATUS_OK, MESSAGE_DB_AVAILABLE, LABEL, productName, productVersion);
            meta = new DbMetadata(summary, details);
        } catch (SQLException e) {
            // TODO logging
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
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            // TODO logging
        }
    }
}
