package com.gd.ashylin.cache.server.command;

/**
 * @author Alexander Shylin
 */
public interface Command {
    String GET = "get";
    String INVALIDATE = "invalidate";
    String INVALIDATE_ALL = "invalidate_all";
    String SIZE = "size";
    String STATS = "stats";
}
