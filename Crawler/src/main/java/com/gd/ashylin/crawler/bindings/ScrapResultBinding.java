package com.gd.ashylin.crawler.bindings;

/**
 * @author Alexander Shylin
 */
public class ScrapResultBinding {
    private long id;
    private String url;
    private String sourceUrl;
    private String status;
    private long responseTime;

    public ScrapResultBinding() {}

    public ScrapResultBinding(long id, String url, String sourceUrl, String status, long responseTime) {
        this.id = id;
        this.url = url;
        this.sourceUrl = sourceUrl;
        this.status = status;
        this.responseTime = responseTime;
    }

    public long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public String getStatus() {
        return status;
    }

    public long getResponseTime() {
        return responseTime;
    }
}
