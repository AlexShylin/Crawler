package com.gd.ashylin.crawler.db.dao;

import com.gd.ashylin.crawler.db.entity.DbMetadata;
import com.gd.ashylin.crawler.db.entity.Details;
import com.gd.ashylin.crawler.db.entity.Summary;
import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2DbMetadataDAO implements DbMetadataDAO {

    // connection resources
    private Connection connection;
    private DataSource dataSource;

    // String resources
    private static final String LABEL = "H2 database";

    // common fields
    DatabaseMetaData databaseMetaData;


    public H2DbMetadataDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    /*
     * Implemented methods
     */

    public DbMetadata getDbMetadata() {
        DbMetadata meta;
        Summary summary;
        Details details;

        try {
            establishConnection();
            databaseMetaData = connection.getMetaData();

            String productName = databaseMetaData.getDatabaseProductName();
            String productVersion = databaseMetaData.getDatabaseProductVersion();

            // if no exception, everything is ok
            summary = new Summary(STATUS_OK, MESSAGE_HEALTH_PASSED);
            details = new Details(STATUS_OK, MESSAGE_DB_AVAILABLE, LABEL, productName, productVersion);
            meta = new DbMetadata(summary, details);
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
            // TODO logging

            summary = new Summary(STATUS_ERROR, MESSAGE_HEALTH_DB_FAIL);
            details = new Details(STATUS_ERROR, MESSAGE_DB_UNAVAILABLE, LABEL, STATUS_ERROR, STATUS_ERROR);
            meta = new DbMetadata(summary, details);
        } finally {
            closeConnection();
        }

        return meta;
    }



    /*
     * Connection control
     */

    private void establishConnection() throws SQLException {
        if (dataSource instanceof BasicDataSource) {
            BasicDataSource bds = (BasicDataSource) dataSource;
            connection = DriverManager.getConnection(bds.getUrl(), bds.getUsername(), bds.getPassword());
        } else {
            connection = dataSource.getConnection();
        }
    }

    private void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // TODO logging
        }
    }
}
