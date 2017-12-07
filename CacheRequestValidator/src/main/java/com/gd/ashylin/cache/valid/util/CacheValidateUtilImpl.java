package com.gd.ashylin.cache.valid.util;

/**
 * @author Alexander Shylin
 */
public class CacheValidateUtilImpl implements CacheValidateUtil {

    public static final String NUMBER_VALUE_ERROR_MES = "TTL can't be %, it must be >= 0";
    public static final String NULL_PARAM_MES = "Parameter can't be null";

    @Override
    public void notNull(Object... values) {
        for (Object value : values) {
            if (value == null) {
                throw new IllegalArgumentException(NULL_PARAM_MES);
            }
        }
    }

    @Override
    public void isNotNegativeNumber(Long number) {
        if (number < 0) {
            throw new IllegalArgumentException(String.format(NUMBER_VALUE_ERROR_MES, number));
        }
    }
}
