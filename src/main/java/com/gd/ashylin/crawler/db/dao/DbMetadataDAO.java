package com.gd.ashylin.crawler.db.dao;

import com.gd.ashylin.crawler.db.entity.DbMetadata;

public interface DbMetadataDAO {
    //////////////////////
    // String resources //
    //////////////////////
    String STATUS_OK = "OK";
    String STATUS_ERROR = "ERROR";

    String MESSAGE_HEALTH_PASSED = "All health checks passed";
    String MESSAGE_HEALTH_DB_FAIL = "Database health check failed";

    String MESSAGE_DB_AVAILABLE = "DB available";
    String MESSAGE_DB_UNAVAILABLE = "DB unavailable";

    DbMetadata getDbMetadata();
}
