package com.gd.ashylin.cache.valid;

/**
 * @author Alexander Shylin
 */
public interface CacheRequestValidator {

    void validatePutRequest(Object key, Long ttl);

    void validateGetRequest(Object key, String command);

    void validateCommandRequest(String command);
}
