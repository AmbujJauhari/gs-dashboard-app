package com.ambuj.controller;

import com.ambuj.domain.SpaceLookUpDetails;
import com.ambuj.domain.SpaceLookUpDto;
import com.ambuj.service.SpaceLookUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;


@Controller
public class DashboardController {

    @Autowired
    private SpaceLookUpService spaceLookUpService;


    @RequestMapping(value = "getEnvList", method = GET)
    public @ResponseBody
    SpaceLookUpDto helloWorld() {
        return spaceLookUpService.gsLookUpDetails();
    }

//    @RequestMapping(value = "/", method = RequestMethod.GET)
//    public String index() {
//        return "redirect:/index";
//    }


}
