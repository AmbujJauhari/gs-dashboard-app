package com.ambuj.exception;

public class ConfigLoadException extends RuntimeException {

    public ConfigLoadException() {
        super();
    }

    public ConfigLoadException(Exception e) {
        super(e);
    }
    public ConfigLoadException(String configName) {
        super("Error loading config : " + configName);
    }
}
