package com.gd.ashylin.crawler.db.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Alexander Shylin
 */
public class Result {
    private long id;
    private String status;
    private Timestamp timestampLaunch;
    private Timestamp timestampFinish;
    private String url;
    private int threads;
    private long delay;
    private List<ScrapResult> results;

    public static final String STATUS_PROCESSING = "processing";
    public static final String STATUS_CANCELED = "canceled";
    public static final String STATUS_FINISHED = "finished";

    public Result(String url, int threads, long delay) {
        this.status = STATUS_PROCESSING;
        this.timestampLaunch = new Timestamp(new Date().getTime());
        this.url = url;
        this.threads = threads;
        this.delay = delay;
        this.results = Collections.synchronizedList(new ArrayList<>());
    }

    public Result(long id, String status, Timestamp timestampLaunch, Timestamp timestampFinish, String url, int threads, long delay) {
        this.id = id;
        this.status = status;
        this.timestampLaunch = timestampLaunch;
        this.timestampFinish = timestampFinish;
        this.url = url;
        this.threads = threads;
        this.delay = delay;
        this.results = Collections.synchronizedList(new ArrayList<>());
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

    public Timestamp getTimestampLaunch() {
        return timestampLaunch;
    }

    public Timestamp getTimestampFinish() {
        return timestampFinish;
    }

    public void setTimestampFinish(Date timestampFinish) {
        this.timestampFinish = new Timestamp(timestampFinish.getTime());
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

    public List<ScrapResult> getResults() {
        return results;
    }

    public void setResults(List<ScrapResult> results) {
        this.results = results;
    }

    public boolean contains(ScrapResult o) {
        return results.contains(o);
    }

    public boolean add(ScrapResult scrapResult) {
        if (!contains(scrapResult)) {
            return results.add(scrapResult);
        }
        return false;
    }
}
