package com.ambuj.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Aj on 14-06-2016.
 */
@ControllerAdvice
public class RestExceptionProcessor {

    @ExceptionHandler(ConfigNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorInfo configNotFound(HttpServletRequest httpServletRequest, ConfigNotFoundException e) {
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setErrorMessage(e.getMessage());

        return errorInfo;
    }
}
