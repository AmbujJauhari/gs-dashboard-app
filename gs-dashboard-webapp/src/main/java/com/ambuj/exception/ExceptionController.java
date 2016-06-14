package com.ambuj.exception;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Aj on 15-06-2016.
 */
@Controller
public class ExceptionController {

    @RequestMapping(value = "error/ErrorModal", method = RequestMethod.GET)
    public String errorAbsolute() {
        return "error/ErrorModal";
    }
}
