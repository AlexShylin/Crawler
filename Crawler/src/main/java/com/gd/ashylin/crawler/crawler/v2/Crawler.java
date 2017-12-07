package com.gd.ashylin.crawler.crawler.v2;

/**
 * @author Alexander Shylin
 */
public interface Crawler extends Runnable {
    /**
     * Runs crawler in the same thread (not creates new thread)
     */
    void runInCurrentThread();
}
