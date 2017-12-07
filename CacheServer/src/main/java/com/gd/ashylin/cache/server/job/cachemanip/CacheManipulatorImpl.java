package com.gd.ashylin.cache.server.job.cachemanip;

import com.gd.ashylin.cache.server.bo.ValueBO;
import com.gd.ashylin.cache.server.job.CacheJobExecutorImpl;
import com.gd.ashylin.cache.server.stat.Stat;

import java.util.Map;

/**
 * @author Alexander Shylin
 */
public class CacheManipulatorImpl implements CacheManipulator {

    private Map<String, ValueBO> cache;

    public CacheManipulatorImpl(CacheJobExecutorImpl producer) {
        cache = producer.getCache();
    }

    @Override
    public ValueBO get(String key) {
        ValueBO valueBO = cache.get(key);
        if (valueBO != null) {
            valueBO.setLastRequestTime(System.currentTimeMillis());
            Stat.hitCount.incrementAndGet();
        } else {
            Stat.missCount.incrementAndGet();
        }
        return valueBO;
    }

    @Override
    public void remove(String key) {
        cache.remove(key);
        Stat.evictionCount.incrementAndGet();
    }


    @Override
    public void removeAll() {
        cache.clear();
    }

    @Override
    public void put(String key, String value, Long ttl) {
        ValueBO valueBO = new ValueBO();
        valueBO.setValue(value);
        valueBO.setTtl(ttl);

        cache.put(key, valueBO);
        Stat.loadSuccessCount.incrementAndGet();
    }

    @Override
    public int size() {
        return cache.size();
    }


}
