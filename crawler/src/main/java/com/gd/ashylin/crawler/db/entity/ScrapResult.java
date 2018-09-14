package com.gd.ashylin.crawler.db.entity;

/**
 * @author Alexander Shylin
 */
public class ScrapResult {
    private long id;
    private String url;
    private String sourceUrl;
    private String status;
    private long responseTime;

    public ScrapResult() {
    }

    public ScrapResult(long id, String url, String sourceUrl, String status, long responseTime) {
        this.id = id;
        this.url = url;
        this.sourceUrl = sourceUrl;
        this.status = status;
        this.responseTime = responseTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSourceurl() {
        return sourceUrl;
    }

    public void setSourceurl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(long responseTime) {
        this.responseTime = responseTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScrapResult that = (ScrapResult) o;

        return url.equals(that.url);
    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }
}
