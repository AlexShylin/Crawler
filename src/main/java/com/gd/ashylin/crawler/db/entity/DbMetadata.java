package com.gd.ashylin.crawler.db.entity;

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

    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }
}
