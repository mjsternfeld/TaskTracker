package com.TaskTracker.util;

import lombok.Data;

//valid auth requests receive JWTs as responses

@Data
public class AuthenticationResponse {
    private final String jwt;
}
