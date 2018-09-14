package com.gd.ashylin.crawler.exception;

/**
 * @author Alexander Shylin
 */
public class RequestParsingException extends RuntimeException {
    private String name;
    private String code;

    public RequestParsingException(String name, String code, String message) {
        super(message);
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}