package com.gd.ashylin.crawler.exception;

/**
 * @author Alexander Shylin
 */
public class RelaunchCrawlerException extends RuntimeException {
    private static final String message = "Stopped crawler cannot be launched again";

    public RelaunchCrawlerException() {
        super(message);
    }
}
