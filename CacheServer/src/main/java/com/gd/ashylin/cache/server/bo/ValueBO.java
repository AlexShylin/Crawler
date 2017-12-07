package com.gd.ashylin.cache.server.bo;

/**
 * @author Alexander Shylin
 */
public class ValueBO {
    private String value;
    private Long ttl;
    private Long lastRequestTime = System.currentTimeMillis();

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getTtl() {
        return ttl;
    }

    public void setTtl(Long ttl) {
        this.ttl = ttl;
    }

    public Long getLastRequestTime() {
        return lastRequestTime;
    }

    public void setLastRequestTime(Long lastRequestTime) {
        this.lastRequestTime = lastRequestTime;
    }

    @Override
    public String toString() {
        return value;
    }
}
