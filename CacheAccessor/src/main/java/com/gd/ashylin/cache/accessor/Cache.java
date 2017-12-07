package com.gd.ashylin.cache.accessor;

import com.gd.ashylin.cache.accessor.bo.StatBO;
import com.gd.ashylin.cache.accessor.parse.Parser;
import com.gd.ashylin.cache.valid.CacheRequestValidator;

/**
 * @author Alexander Shylin
 */
public interface Cache<K, V> {

    /**
     * Put value into cache with default time-to-live
     *
     * @param key   key with which the specified value is to be associated
     * @param value data to be stored in cache
     * @return <tt>true</tt> if value was added
     */
    void put(K key, V value);

    /**
     * Put value into cache with specified time-to-live
     *
     * @param key   key with which the specified value is to be associated
     * @param value data to be stored in cache
     * @param ttl   time-to-live in milliseconds
     * @return <tt>true</tt> if value was added
     */
    void put(K key, V value, long ttl);

    /**
     * Returns the value to which the specified key is mapped,
     * or {@code null} if this cache doesn't contain specified key.
     *
     * @param key key with which the specified value is to be associated
     * @return the value to which the specified key is mapped, or {@code null} if this cache doesn't contain specified key.
     */
    V getIfContains(K key);

    /**
     * Removes the mapping for a key from this cache if it is present.
     *
     * @param key key with which the specified value is to be associated
     */
    void invalidate(K key);

    /**
     * Removes all data from the cache.
     */
    void invalidateAll();

    /**
     * Returns the number of elements in the cache.  If the
     * map contains more than <tt>Integer.MAX_VALUE</tt> elements, returns
     * <tt>Integer.MAX_VALUE</tt>.
     *
     * @return the number of elements in the cache
     */
    int size();

    default StatBO cacheStats() {
        throw new UnsupportedOperationException();
    }

    default String cacheStatsAsJson() {
        throw new UnsupportedOperationException();
    }


    void setIp(String ip);

    void setPort(int port);

    void setParser(Parser<K, V> parser);

    void setCacheRequestValidator(CacheRequestValidator cacheRequestValidator);
}
