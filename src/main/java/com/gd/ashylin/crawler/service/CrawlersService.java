package com.gd.ashylin.crawler.service;

/**
 * @author Alexander Shylin
 */
public interface CrawlersService {
    String crawler(String url, int threadsNum, long delay);
    String suspend(long id);
}
