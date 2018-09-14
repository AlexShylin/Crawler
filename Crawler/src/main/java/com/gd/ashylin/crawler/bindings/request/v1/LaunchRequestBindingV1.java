package com.gd.ashylin.crawler.bindings.request.v1;

/**
 * @author Alexander Shylin
 */
public class LaunchRequestBindingV1 {
    private String url;
    private Long delay;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }
}
