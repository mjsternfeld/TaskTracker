package com.TaskTracker.controller;

import com.TaskTracker.model.User;
import com.TaskTracker.repo.UserRepository;
import com.TaskTracker.service.UserService;
import com.TaskTracker.util.AuthenticationRequest;
import com.TaskTracker.util.AuthenticationResponse;
import com.TaskTracker.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

//this contains the handlers for login / register requests

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager; //defined in the SecurityConfig

    @Autowired
    private PasswordEncoder passwordEncoder; //defined in the SecurityConfig

    @Autowired
    private JwtUtil jwtUtil; //used to handle the JWTs

    @Autowired
    private UserService service;




    //sent from the login homepage. Request contains username and password
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
        //extract fields from the request body
        String username = authenticationRequest.getUsername();
        String rawPassword = authenticationRequest.getPassword();


        //try fetching the user info from the repository
        User user = service.findByUsername(username);
        if (user == null) //return 404, Username not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username doesn't exist");
            //this ensures the user is defined


        //check if password is incorrect and return 401 unauthorized
        if (!passwordEncoder.matches(rawPassword, user.getPassword()))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect password");

        //authenticate user with Spring Security
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, rawPassword)
        );

        //generate the JWT
        final String jwt = jwtUtil.generateToken(username);

        //return JWT with 200 OK
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    //sent from the login homepage. Request contains username and password
    @PostMapping("/register")
    public ResponseEntity<?> createUserAccount(@RequestBody AuthenticationRequest authenticationRequest) {
        //extract fields from the request body
        String username = authenticationRequest.getUsername();
        String rawPassword = authenticationRequest.getPassword();

        //check if username is present
        if (service.findByUsername(username) != null)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");


        User savedUser = service.registerUser(username, rawPassword);
        if(savedUser != null)
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
        else
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body("This should not happen actually");
    }

}
