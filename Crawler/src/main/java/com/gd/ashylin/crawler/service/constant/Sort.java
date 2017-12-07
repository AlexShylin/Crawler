package com.gd.ashylin.crawler.service.constant;

/**
 * @author Alexander Shylin
 */
public enum Sort {
    ASC("asc"),
    DESC("desc");

    private final String name;

    Sort(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
