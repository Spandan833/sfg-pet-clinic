package com.springframework.sfgpetclinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping({"","/","index","index.html"})
    public String index(){
        return "welcome.html"; //looks for index.html in templates folder
    }

    @RequestMapping({"/oups"})
    public String ooops(){
        return "notimplemented.html";
    }
}
