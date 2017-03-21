package com.gd.ashylin.crawler.db.entity;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author Alexander Shylin
 */
public class Crawler {
    private long id;
    private String status;
    private SimpleDateFormat dateLaunch;
    private SimpleDateFormat dateFinish;
    private String url;
    private int threads;
    private long delay;
    private List<ScrapResult> scrapResults;

    public Crawler(long id, String status, SimpleDateFormat dateLaunch, SimpleDateFormat dateFinish, String url, int threads, long delay, List<ScrapResult> scrapResults) {
        this.id = id;
        this.status = status;
        this.dateLaunch = dateLaunch;
        this.dateFinish = dateFinish;
        this.url = url;
        this.threads = threads;
        this.delay = delay;
        this.scrapResults = scrapResults;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SimpleDateFormat getDateLaunch() {
        return dateLaunch;
    }

    public void setDateLaunch(SimpleDateFormat dateLaunch) {
        this.dateLaunch = dateLaunch;
    }

    public SimpleDateFormat getDateFinish() {
        return dateFinish;
    }

    public void setDateFinish(SimpleDateFormat dateFinish) {
        this.dateFinish = dateFinish;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getThreads() {
        return threads;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public List<ScrapResult> getScrapResults() {
        return scrapResults;
    }

    public void setScrapResults(List<ScrapResult> scrapResults) {
        this.scrapResults = scrapResults;
    }
}
