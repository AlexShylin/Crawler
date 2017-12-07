package com.gd.ashylin.cache.accessor.parse;

import com.gd.ashylin.cache.accessor.binding.request.*;

/**
 * @author Alexander Shylin
 */
public interface Parser<K, V> {
    PutRequestBinding parseToPutRequest(K key, V value, Long ttl);

    GetRequestBinding parseToGetRequest(K key, String command);

    CommandRequestBinding parseToCommandRequest(String command);

    String parseRequestBindingToString(RequestBinding requestBinding);

    V parseResponseToObject(String response);

    StatRequestBinding parseStatToObject(String stat);
}
