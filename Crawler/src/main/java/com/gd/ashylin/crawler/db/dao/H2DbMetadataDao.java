package com.gd.ashylin.crawler.db.dao;

import com.gd.ashylin.crawler.db.entity.metadata.DbMetadata;
import com.gd.ashylin.crawler.db.entity.metadata.Details;
import com.gd.ashylin.crawler.db.entity.metadata.Summary;
import org.springframework.jdbc.support.DatabaseMetaDataCallback;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.MetaDataAccessException;

import javax.sql.DataSource;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

/**
 * @author Alexander Shylin
 */
public class H2DbMetadataDao implements DbMetadataDao {

    private static final String LABEL = "H2 database";

    private DataSource dataSource;


    /*
     * Implemented methods
     */

    @Override
    public DbMetadata getDbMetadata() {

        Object meta;
        try {
            meta = JdbcUtils.extractDatabaseMetaData(dataSource, new DatabaseMetaDataCallback() {
                @Override
                public Object processMetaData(DatabaseMetaData dbmd) throws SQLException, MetaDataAccessException {
                    String productName = dbmd.getDatabaseProductName();
                    String productVersion = dbmd.getDatabaseProductVersion();

                    Summary summary = new Summary(STATUS_OK, MESSAGE_HEALTH_PASSED);
                    Details details = new Details(STATUS_OK, MESSAGE_DB_AVAILABLE, LABEL, productName, productVersion);
                    return new DbMetadata(summary, details);
                }
            });
        } catch (MetaDataAccessException e) {
            Summary summary = new Summary(STATUS_ERROR, MESSAGE_HEALTH_DB_FAIL);
            Details details = new Details(STATUS_ERROR, MESSAGE_DB_UNAVAILABLE, LABEL, STATUS_ERROR, STATUS_ERROR);
            meta = new DbMetadata(summary, details);
        }

        return (DbMetadata) meta;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
