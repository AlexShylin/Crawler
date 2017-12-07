package com.gd.ashylin.crawler.dto;

/**
 * @author Alexander Shylin
 */
public class CacheDto {
    private String status;
    private Long responseTime;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Long responseTime) {
        this.responseTime = responseTime;
    }
}
