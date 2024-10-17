package com.TaskTracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FrontendController {

    @GetMapping("/{path:[^\\.]*}") // Match any route that doesn't contain a dot (.) like .js or .css
    public String redirect() {
        return "forward:/index.html"; // Forward to index.html
    }
}
