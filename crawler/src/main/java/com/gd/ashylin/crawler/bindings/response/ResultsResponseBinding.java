package com.gd.ashylin.crawler.bindings.response;

import com.gd.ashylin.crawler.bindings.ScrapResultBinding;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author Alexander Shylin
 */
public class ResultsResponseBinding {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm a");

    private long id;
    private String status;
    private Timestamp timestampLaunch;
    private Timestamp timestampFinish;
    private String url;
    private int threads;
    private long delay;
    private List<ScrapResultBinding> results;

    public ResultsResponseBinding() {
    }

    public ResultsResponseBinding(long id, String status, Timestamp timestampLaunch, Timestamp timestampFinish, String url, int threads, long delay, List<ScrapResultBinding> results) {
        this.id = id;
        this.status = status;
        this.timestampLaunch = timestampLaunch;
        this.timestampFinish = timestampFinish;
        this.url = url;
        this.threads = threads;
        this.delay = delay;
        this.results = results;
    }

    public long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getTimestampLaunch() {
        return sdf.format(timestampLaunch);
    }

    public String getTimestampFinish() {
        return sdf.format(timestampFinish);
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

    public List<ScrapResultBinding> getResults() {
        return results;
    }
}
