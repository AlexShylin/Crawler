package com.gd.ashylin.crawler.db.dao;

import com.gd.ashylin.crawler.db.entity.DbMetadata;
import com.gd.ashylin.crawler.db.entity.Details;
import com.gd.ashylin.crawler.db.entity.Summary;
import org.apache.commons.dbcp.BasicDataSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Properties;

public class H2DbMetadataDAO implements DbMetadataDAO {

//    private Context context;
//    private DataSource dataSource;
    private BasicDataSource basicDataSource;
    private Connection connection;
    private DatabaseMetaData databaseMetaData;

    // String resources
    private static final String LABEL = "H2 database";

    // Connection credentials
    private String connectionString;
    private String user;
    private String password;
    private String driver;
    private Properties properties;

//    static {
//        try {
//            Class.forName("org.h2.Driver");
//        } catch (ClassNotFoundException e) {
//            // TODO logging
//        }
//    }

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
            driver = properties.getProperty("driver");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    {
        basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(driver);
        basicDataSource.setUsername(user);
        basicDataSource.setPassword(password);
        basicDataSource.setUrl(connectionString);

        basicDataSource.setMinIdle(5);
        basicDataSource.setMaxIdle(20);
        basicDataSource.setMaxOpenPreparedStatements(180);
    }


    public DbMetadata getDbMetadata() {
        DbMetadata meta;
        Summary summary;
        Details details;

        try {
//            connection = DriverManager.getConnection(connectionString, user, password);
//            context = new InitialContext();
//            dataSource = (DataSource) context.lookup("jdbc/h2");
//            connection = dataSource.getConnection();
            connection = basicDataSource.getConnection();

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
