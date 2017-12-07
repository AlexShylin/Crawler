package com.gd.ashylin.cache.server.binding.request;

/**
 * @author Alexander Shylin
 */
public class PutRequestBinding implements RequestBinding {
    private String key;
    private String value;
    private Long ttl;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

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
}
