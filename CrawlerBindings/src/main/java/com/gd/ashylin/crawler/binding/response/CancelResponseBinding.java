package com.gd.ashylin.crawler.binding.response;

/**
 * @author Alexander Shylin
 */
public class CancelResponseBinding {
    private String status;

    public CancelResponseBinding(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
