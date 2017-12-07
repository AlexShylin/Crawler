package com.gd.ashylin.cache.server.job.cleaner;

import com.gd.ashylin.cache.server.bo.ValueBO;
import com.gd.ashylin.cache.server.job.CacheJobExecutor;
import com.gd.ashylin.cache.server.stat.Stat;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * @author Alexander Shylin
 */
public class ExpiredTtlDataCleanerImpl implements ExpiredTtlDataCleaner {

    static final Logger LOGGER  = Logger.getLogger(ExpiredTtlDataCleanerImpl.class);

    private CacheJobExecutor jobExecutor;

    private long timeToSleep = ExpiredTtlDataCleaner.DEFAULT_SLEEP_TIME;

    @Override
    public void run() {
        Map<String, ValueBO> cache = jobExecutor.getCache();

        while (true) {
            for (String key : cache.keySet()) {
                ValueBO valueBO = cache.get(key);
                if (isOld(valueBO)) {
                    cache.remove(key);
                    Stat.evictionCount.incrementAndGet();
                }
            }

            try {
                Thread.sleep(timeToSleep);
            } catch (InterruptedException e) {
                LOGGER.error("Cache cleaner sleep error", e);
            }
        }
    }

    @Override
    public void setCacheJobExecutor(CacheJobExecutor executor) {
        jobExecutor = executor;
    }

    public void setTimeToSleep(long timeToSleep) {
        this.timeToSleep = timeToSleep;
    }


    private boolean isOld(ValueBO valueBO) {
        long ttl = valueBO.getTtl();
        long lastRequestTime = valueBO.getLastRequestTime();
        long currentTime = System.currentTimeMillis();
        return currentTime - lastRequestTime >= ttl;
    }
}
