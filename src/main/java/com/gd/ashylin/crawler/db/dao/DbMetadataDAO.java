package com.gd.ashylin.crawler.db.dao;

import com.gd.ashylin.crawler.db.entity.metadata.DbMetadata;

/**
 * @author Alexander Shylin
 */
public interface DbMetadataDAO {
    //////////////////////
    // String resources //
    //////////////////////
    String STATUS_OK = "OK";
    String STATUS_ERROR = "ERROR";

    String MESSAGE_HEALTH_PASSED = "All service checks passed";
    String MESSAGE_HEALTH_DB_FAIL = "Database service check failed";

    String MESSAGE_DB_AVAILABLE = "DB available";
    String MESSAGE_DB_UNAVAILABLE = "DB unavailable";

    DbMetadata getDbMetadata();
}
