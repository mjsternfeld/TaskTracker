package com.TaskTracker.util;


import lombok.Data;

//this is in the login / register request bodies

@Data
public class AuthenticationRequest {

    private String username;
    private String password;

}
