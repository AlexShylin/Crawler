package com.gd.ashylin.crawler.bindings.response;

/**
 * @author Alexander Shylin
 */
public class LaunchResponseBinding {
    private long id;

    public LaunchResponseBinding() {
    }

    public LaunchResponseBinding(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
