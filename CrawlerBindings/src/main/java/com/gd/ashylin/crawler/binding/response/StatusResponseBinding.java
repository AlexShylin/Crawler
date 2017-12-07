package com.gd.ashylin.crawler.binding.response;

/**
 * @author Alexander Shylin
 */
public class StatusResponseBinding {
    private String status;

    public StatusResponseBinding() {
    }

    public StatusResponseBinding(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
