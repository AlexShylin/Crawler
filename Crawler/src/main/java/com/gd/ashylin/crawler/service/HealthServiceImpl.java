package com.gd.ashylin.crawler.service;

import com.gd.ashylin.crawler.db.dao.DbMetadataDao;
import com.gd.ashylin.crawler.db.entity.metadata.DbMetadata;
import org.apache.log4j.Logger;

/**
 * @author Alexander Shylin
 */
public class HealthServiceImpl implements HealthService {

    static final Logger LOGGER = Logger.getLogger(HealthServiceImpl.class);

    private DbMetadataDao dbMetadataDao;

    public DbMetadata healthCheck() {
        return dbMetadataDao.getDbMetadata();
    }

    public void setDbMetadataDao(DbMetadataDao dbMetadataDao) {
        this.dbMetadataDao = dbMetadataDao;
    }
}
