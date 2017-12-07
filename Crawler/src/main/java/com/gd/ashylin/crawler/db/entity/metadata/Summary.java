package com.gd.ashylin.crawler.db.entity.metadata;

/**
 * @author Alexander Shylin
 */
public class Summary {
    private String status;
    private String message;

    public Summary(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
