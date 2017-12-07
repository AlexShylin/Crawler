package com.gd.ashylin.cache.server.job.cachemanip;

import com.gd.ashylin.cache.server.bo.ValueBO;

/**
 * @author Alexander Shylin
 */
public interface CacheManipulator {
    ValueBO get(String key);

    void remove(String key);

    void put(String key, String value, Long ttl);

    void removeAll();

    int size();

    default Object stat() {
        throw new UnsupportedOperationException();
    }
}
