package com.gd.ashylin.cache.server.stat;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Alexander Shylin
 */
public class Stat {
    public static final AtomicLong hitCount = new AtomicLong(0);
    public static final AtomicLong missCount = new AtomicLong(0);
    public static final AtomicLong loadSuccessCount = new AtomicLong(0);
    public static final AtomicLong loadExceptionCount = new AtomicLong(0);
    public static final AtomicLong evictionCount = new AtomicLong(0);

    private static final long startTime = System.currentTimeMillis();

    public static long getTotalLoadTime() {
        return System.currentTimeMillis() - startTime;
    }
}
