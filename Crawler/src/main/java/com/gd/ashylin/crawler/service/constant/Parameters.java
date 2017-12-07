package com.gd.ashylin.crawler.service.constant;

/**
 * @author Alexander Shylin
 */
public enum Parameters {
    ID("id_job"),
    DELAY("delay"),
    OFFSET("offset"),
    PAGE_SIZE("page_size"),
    URL("url"),
    SORT("sort"),
    SEARCH("search"),
    FIELD("field"),
    THREADS("threads");

    private final String name;

    Parameters(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
