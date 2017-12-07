package com.gd.ashylin.cache.accessor.binding.request;

/**
 * @author Alexander Shylin
 */
public class CommandRequestBinding implements RequestBinding {
    private String command;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
