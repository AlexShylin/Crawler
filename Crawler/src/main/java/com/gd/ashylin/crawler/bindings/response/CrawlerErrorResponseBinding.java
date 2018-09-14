package com.gd.ashylin.crawler.bindings.response;

/**
 * @author Alexander Shylin
 */
public class CrawlerErrorResponseBinding {
    private String name;
    private String code;
    private String description;

    public CrawlerErrorResponseBinding(String name, String code, String description) {
        this.name = name;
        this.code = code;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
