package com.gd.ashylin.cache.server.job;

import com.gd.ashylin.cache.server.job.cleaner.ExpiredTtlDataCleaner;

import java.util.Map;

/**
 * @author Alexander Shylin
 */
public interface CacheJobExecutor {

    // 10 min
    long DEFAULT_TTL = 600000;

    void init();

    void setPort(int port);

    void setInitialCacheSize(int initialSize);

    void setMaxPoolSize(int size);

    void setCleaner(ExpiredTtlDataCleaner cleaner);

    Map getCache();
}
