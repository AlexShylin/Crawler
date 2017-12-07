package com.gd.ashylin.cache.server.job.cleaner;

import com.gd.ashylin.cache.server.job.CacheJobExecutor;

/**
 * @author Alexander Shylin
 */
public interface ExpiredTtlDataCleaner extends Runnable {
    // 1 minute
    long DEFAULT_SLEEP_TIME = 60000;

    void setCacheJobExecutor(CacheJobExecutor executor);

    void setTimeToSleep(long timeToSleep);
}
