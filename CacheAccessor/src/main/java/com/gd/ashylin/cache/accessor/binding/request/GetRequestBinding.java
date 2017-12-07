package com.gd.ashylin.cache.accessor.binding.request;

/**
 * @author Alexander Shylin
 */
public class GetRequestBinding implements RequestBinding {
    private String key;
    private String command;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
