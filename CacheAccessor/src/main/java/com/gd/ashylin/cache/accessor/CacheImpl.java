package com.gd.ashylin.cache.accessor;

import com.gd.ashylin.cache.accessor.binding.request.CommandRequestBinding;
import com.gd.ashylin.cache.accessor.binding.request.GetRequestBinding;
import com.gd.ashylin.cache.accessor.binding.request.PutRequestBinding;
import com.gd.ashylin.cache.accessor.binding.request.StatRequestBinding;
import com.gd.ashylin.cache.accessor.bo.StatBO;
import com.gd.ashylin.cache.accessor.command.Command;
import com.gd.ashylin.cache.accessor.parse.Parser;
import com.gd.ashylin.cache.accessor.socket.SocketFacade;
import com.gd.ashylin.cache.valid.CacheRequestValidator;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.Socket;

/**
 * @author Alexander Shylin
 */
public class CacheImpl<K, V> implements Cache<K, V> {

    private String ip;
    private int port;

    private Parser<K, V> parser;
    private CacheRequestValidator cacheRequestValidator;

    @Override
    public void put(K key, V value) {
        put(key, value, 0L);
    }

    @Override
    public void put(K key, V value, long ttl) {
        SocketFacade socketFacade = new SocketFacade(connect());

        cacheRequestValidator.validatePutRequest(key, ttl);
        PutRequestBinding requestBinding = parser.parseToPutRequest(key, value, 0L);
        String request = parser.parseRequestBindingToString(requestBinding);
        socketFacade.sendData(request);

        socketFacade.close();
    }

    @Override
    public V getIfContains(K key) {
        SocketFacade socketFacade = new SocketFacade(connect());
        String com = Command.GET;

        cacheRequestValidator.validateGetRequest(key, com);
        GetRequestBinding requestBinding = parser.parseToGetRequest(key, com);
        String request = parser.parseRequestBindingToString(requestBinding);
        socketFacade.sendData(request);

        String response = socketFacade.getData();
        if (response == null || "null".equals(response)) {
            return null;
        }

        socketFacade.close();
        return parser.parseResponseToObject(response);
    }

    @Override
    public void invalidate(K key) {
        SocketFacade socketFacade = new SocketFacade(connect());
        String com = Command.INVALIDATE;

        cacheRequestValidator.validateGetRequest(key, com);
        GetRequestBinding requestBinding = parser.parseToGetRequest(key, com);
        String request = parser.parseRequestBindingToString(requestBinding);
        socketFacade.sendData(request);

        socketFacade.close();
    }

    @Override
    public void invalidateAll() {
        SocketFacade socketFacade = new SocketFacade(connect());
        String com = Command.INVALIDATE_ALL;

        cacheRequestValidator.validateCommandRequest(com);
        CommandRequestBinding requestBinding = parser.parseToCommandRequest(com);
        String request = parser.parseRequestBindingToString(requestBinding);
        socketFacade.sendData(request);

        socketFacade.close();
    }

    @Override
    public int size() {
        SocketFacade socketFacade = new SocketFacade(connect());
        String com = Command.SIZE;

        cacheRequestValidator.validateCommandRequest(com);
        CommandRequestBinding requestBinding = parser.parseToCommandRequest(com);
        String request = parser.parseRequestBindingToString(requestBinding);
        socketFacade.sendData(request);

        String response = socketFacade.getData();
        socketFacade.close();
        return Integer.parseInt(response);
    }

    @Override
    public StatBO cacheStats() {
        String json = cacheStatsAsJson();
        StatRequestBinding requestBinding = parser.parseStatToObject(json);

        if (requestBinding != null) {
            StatBO statBO = new StatBO();

            statBO.setLoadExceptionCount(requestBinding.getLoadExceptionCount());
            statBO.setTotalLoadTime(requestBinding.getTotalLoadTime());
            statBO.setLoadSuccessCount(requestBinding.getLoadSuccessCount());
            statBO.setMissCount(requestBinding.getMissCount());
            statBO.setHitCount(requestBinding.getHitCount());
            statBO.setEvictionCount(requestBinding.getEvictionCount());

            return statBO;
        } else {
            return null;
        }
    }

    @Override
    public String cacheStatsAsJson() {
        SocketFacade socketFacade = new SocketFacade(connect());
        String com = Command.STATS;

        cacheRequestValidator.validateCommandRequest(com);
        CommandRequestBinding requestBinding = parser.parseToCommandRequest(com);
        String request = parser.parseRequestBindingToString(requestBinding);
        socketFacade.sendData(request);

        String response = socketFacade.getData();
        socketFacade.close();

        return response;
    }


    private Socket connect() {
        try {
            return new Socket(ip, port);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public void setParser(Parser<K, V> parser) {
        this.parser = parser;
    }

    @Override
    public void setCacheRequestValidator(CacheRequestValidator cacheRequestValidator) {
        this.cacheRequestValidator = cacheRequestValidator;
    }
}
