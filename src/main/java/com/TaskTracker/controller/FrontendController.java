package com.TaskTracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FrontendController {

    //this is used to get the REACT frontend app to the user from the root url

    @GetMapping("/{path:[^\\.]*}") //match any route that doesn't contain a dot (.) like .js or .css
    public String redirect() {
        return "forward:/index.html"; //forward to index.html / homepage
    }
}
