package com.gd.ashylin.crawler.dto;

import com.gd.ashylin.crawler.db.entity.Result;
import com.gd.ashylin.crawler.db.entity.ScrapResult;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author Alexander Shylin
 */
public class ResultsDto {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm a");

    private long id;
    private String status;
    private Timestamp timestampLaunch;
    private Timestamp timestampFinish;
    private String url;
    private int threads;
    private long delay;
    private List<ScrapResult> results;

    public ResultsDto() {
    }

    public ResultsDto(Result result) {
        this.id = result.getId();
        this.status = result.getStatus();
        this.timestampLaunch = result.getTimestampLaunch();
        this.timestampFinish = result.getTimestampFinish();
        this.url = result.getUrl();
        this.threads = result.getThreads();
        this.delay = result.getDelay();
        this.results = result.getResults();
    }

    public long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public Timestamp getTimestampLaunch() {
        return timestampLaunch;
    }

    public Timestamp getTimestampFinish() {
        return timestampFinish;
    }

    public String getUrl() {
        return url;
    }

    public int getThreads() {
        return threads;
    }

    public long getDelay() {
        return delay;
    }

    public List<ScrapResult> getResults() {
        return results;
    }
}
