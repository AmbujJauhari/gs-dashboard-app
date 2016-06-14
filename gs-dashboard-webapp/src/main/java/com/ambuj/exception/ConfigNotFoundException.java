package com.ambuj.exception;

/**
 * Created by Aj on 14-06-2016.
 */
public class ConfigNotFoundException extends RuntimeException {

    public ConfigNotFoundException() {
        super();
    }

    public ConfigNotFoundException(String directory) {
        super("No configuration file found under : " + directory);
    }
}
