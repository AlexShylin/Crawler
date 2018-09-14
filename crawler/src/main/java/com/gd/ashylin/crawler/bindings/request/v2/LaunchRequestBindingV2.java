package com.gd.ashylin.crawler.bindings.request.v2;

/**
 * @author Alexander Shylin
 */
public class LaunchRequestBindingV2 {
    private String url;
    private Integer threads;
    private Long delay;
    private Boolean enableCaching;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getThreads() {
        return threads;
    }

    public void setThreads(Integer threads) {
        this.threads = threads;
    }

    public Long getDelay() {
        return delay;
    }

    public void setDelay(Long delay) {
        this.delay = delay;
    }

    public Boolean getEnableCaching() {
        return enableCaching;
    }

    public void setEnableCaching(Boolean enableCaching) {
        this.enableCaching = enableCaching;
    }
}
