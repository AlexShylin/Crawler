package com.gd.ashylin.crawler.db.dao;

import com.gd.ashylin.crawler.db.entity.metadata.DbMetadata;
import com.gd.ashylin.crawler.db.entity.metadata.Details;
import com.gd.ashylin.crawler.db.entity.metadata.Summary;

import javax.sql.DataSource;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

/**
 * @author Alexander Shylin
 */
public class H2DbMetadataDAO extends ConnectionControl implements DbMetadataDAO {

    // String resources
    private static final String LABEL = "H2 database";

    public H2DbMetadataDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    /*
     * Implemented methods
     */

    @Override
    public DbMetadata getDbMetadata() {
        DbMetadata meta;
        Summary summary;
        Details details;

        try {
            establishConnection();
            DatabaseMetaData databaseMetaData = connection.getMetaData();

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

}
