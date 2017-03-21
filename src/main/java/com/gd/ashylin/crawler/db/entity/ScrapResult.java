package com.gd.ashylin.crawler.db.entity;

/**
 * @author Alexander Shylin
 */
public class ScrapResult {
    private long id;
    private long idCrawler;
    private String url;
    private String sourceUrl;
    private String status;

    public ScrapResult(long id, long idCrawler, String url, String sourceUrl, String status) {
        this.id = id;
        this.idCrawler = idCrawler;
        this.url = url;
        this.sourceUrl = sourceUrl;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdCrawler() {
        return idCrawler;
    }

    public void setIdCrawler(long idCrawler) {
        this.idCrawler = idCrawler;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
