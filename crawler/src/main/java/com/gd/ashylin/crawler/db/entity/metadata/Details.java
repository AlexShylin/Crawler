package com.gd.ashylin.crawler.db.entity.metadata;

/**
 * @author Alexander Shylin
 */
public class Details {
    private DB db;

    public Details(DB db) {
        this.db = db;
    }

    public Details(String status, String message, String label, String name, String ver) {
        db = new DB(status, message, label, name, ver);
    }

    public DB getDb() {
        return db;
    }

    public void setDb(DB db) {
        this.db = db;
    }
}
