package com.ambuj.exception;

/**
 * Created by Aj on 14-06-2016.
 */
public class ErrorInfo {
    private String errorMessage;
    private String stackTrace;

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
