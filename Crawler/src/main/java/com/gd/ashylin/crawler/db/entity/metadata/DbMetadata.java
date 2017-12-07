package com.gd.ashylin.crawler.db.entity.metadata;

/**
 * @author Alexander Shylin
 */
public class DbMetadata {
    private Summary summary;
    private Details details;

    public DbMetadata(Summary summary, Details details) {
        this.summary = summary;
        this.details = details;
    }

    public Summary getSummary() {
        return summary;
    }

    public Details getDetails() {
        return details;
    }
}
