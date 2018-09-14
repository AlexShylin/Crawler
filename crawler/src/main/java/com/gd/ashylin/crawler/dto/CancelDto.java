package com.gd.ashylin.crawler.dto;

/**
 * @author Alexander Shylin
 */
public class CancelDto {
    private String status;

    public CancelDto(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
