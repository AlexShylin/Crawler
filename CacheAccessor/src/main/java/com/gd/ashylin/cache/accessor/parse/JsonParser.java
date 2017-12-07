package com.gd.ashylin.cache.accessor.parse;

import com.gd.ashylin.cache.accessor.binding.request.*;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * @author Alexander Shylin
 */
public class JsonParser<K, V> implements Parser<K, V> {

    private ObjectMapper mapper = new ObjectMapper();
    private Class<V> vClass;


    public JsonParser(Class<V> valueClass) {
        vClass = valueClass;
    }

    @Override
    public PutRequestBinding parseToPutRequest(K key, V value, Long ttl) {
        PutRequestBinding responseBinding = new PutRequestBinding();
        try {
            responseBinding.setKey(mapper.writeValueAsString(key));
            responseBinding.setValue(mapper.writeValueAsString(value));
            responseBinding.setTtl(ttl);
            return responseBinding;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public GetRequestBinding parseToGetRequest(K key, String command) {
        GetRequestBinding responseBinding = new GetRequestBinding();
        try {
            responseBinding.setKey(mapper.writeValueAsString(key));
            responseBinding.setCommand(command);
            return responseBinding;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public CommandRequestBinding parseToCommandRequest(String command) {
        CommandRequestBinding responseBinding = new CommandRequestBinding();
        responseBinding.setCommand(command);
        return responseBinding;
    }

    @Override
    public String parseRequestBindingToString(RequestBinding responseBinding) {
        try {
            return mapper.writeValueAsString(responseBinding);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public V parseResponseToObject(String response) {
        try {
            return mapper.readValue(response, vClass);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public StatRequestBinding parseStatToObject(String stat) {
        try {
            return mapper.readValue(stat, StatRequestBinding.class);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
