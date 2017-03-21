package com.gd.ashylin.crawler.service;

/**
 * @author Alexander Shylin
 */
public interface ResultsService {
    String getResult(long id, long from, long to, String sortingAcsDesc);
    String getStatus(long id);
    String getScrapResult(long id, long scanId);
}
