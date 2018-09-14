package com.gd.ashylin.crawler.service;

import com.gd.ashylin.crawler.db.entity.metadata.DbMetadata;

/**
 * @author Alexander Shylin
 */
public interface HealthService {

    DbMetadata healthCheck();

}
