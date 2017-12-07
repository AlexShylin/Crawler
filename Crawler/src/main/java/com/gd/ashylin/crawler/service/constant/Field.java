package com.gd.ashylin.crawler.service.constant;

/**
 * @author Alexander Shylin
 */
public enum Field {
    URL("url"),
    SOURCE_URL("sourceurl"),
    STATUS("status");

    private final String name;

    Field(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
