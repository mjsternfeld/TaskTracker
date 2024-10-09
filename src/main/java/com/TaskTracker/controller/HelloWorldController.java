package com.TaskTracker.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//this is just for testing purposes to get the REACT frontend to communicate with this Spring backend
@RestController
public class HelloWorldController {

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/hello")
    public String helloWorld(){
        return "Hello world!";
    }

}
