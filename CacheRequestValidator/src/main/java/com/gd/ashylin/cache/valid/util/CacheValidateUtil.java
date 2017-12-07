package com.gd.ashylin.cache.valid.util;

/**
 * @author Alexander Shylin
 */
public interface CacheValidateUtil {
    void notNull(Object... values);

    void isNotNegativeNumber(Long number);
}
