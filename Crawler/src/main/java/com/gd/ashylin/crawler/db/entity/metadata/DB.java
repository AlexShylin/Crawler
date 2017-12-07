package com.gd.ashylin.crawler.db.entity.metadata;

/**
 * @author Alexander Shylin
 */
public class DB {
    private String status;
    private String message;
    private String label;
    private String productName;
    private String productVersion;

    public DB(String status, String message, String label, String productName, String productVersion) {
        this.status = status;
        this.message = message;
        this.label = label;
        this.productName = productName;
        this.productVersion = productVersion;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getLabel() {
        return label;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductVersion() {
        return productVersion;
    }
}
