package com.ambuj.controller;

import com.ambuj.service.SpaceLookUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class DashboardController {

    @Autowired
    private SpaceLookUpService spaceLookUpService;


    @RequestMapping("/index")
    public ModelAndView helloWorld() {
        ModelAndView gsEnvironmentList = new ModelAndView("dashboard");
        gsEnvironmentList.addObject("gsEnvList", spaceLookUpService.gsLookUpDetails());
        return gsEnvironmentList;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "redirect:/index";
    }


}
