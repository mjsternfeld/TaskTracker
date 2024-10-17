package com.TaskTracker.controller;

import com.TaskTracker.model.User;
import com.TaskTracker.repo.UserRepository;
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

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder; // Injecting PasswordEncoder

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository repo;


    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
        String username = authenticationRequest.getUsername();
        String rawPassword = authenticationRequest.getPassword();

        // Fetch the user from the repository
        Optional<User> userOpt = repo.findByUsername(username);

        if (userOpt.isEmpty()) {
            // Return 404 Not Found if the username does not exist
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username doesn't exist");
        }

        User user = userOpt.get();

        // Use PasswordEncoder to check password match
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            // Return 401 Unauthorized if the password is incorrect
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect password");
        }

        // Authenticate user with Spring Security
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, rawPassword)
        );

        // Generate the JWT token
        final String jwt = jwtUtil.generateToken(username);

        // Return 200 OK with the JWT token
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }






    @PostMapping("/register")
    public ResponseEntity<?> createUserAccount(@RequestBody AuthenticationRequest authenticationRequest) {
        String username = authenticationRequest.getUsername();
        String rawPassword = authenticationRequest.getPassword();

        // Check for existing username
        if (repo.findByUsername(username).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }

        // Hash the password using PasswordEncoder
        String hashedPassword = passwordEncoder.encode(rawPassword);

        // Create and save new user
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(hashedPassword);
        repo.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
    }





}
