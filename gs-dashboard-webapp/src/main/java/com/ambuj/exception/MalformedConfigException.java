package com.ambuj.exception;


public class MalformedConfigException extends RuntimeException {

    public MalformedConfigException() {
        super();
    }

    public MalformedConfigException(Exception ex) {
        super(ex);
    }

    public MalformedConfigException(String configName) {
        super("Malformed configuration" + configName);
    }
}
