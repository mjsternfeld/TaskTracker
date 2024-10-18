package com.TaskTracker.service;

import com.TaskTracker.model.User;
import com.TaskTracker.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    //used to access the user table

    @Autowired
    private UserRepository repo;

    //creates a user entry with the username and (hashed) password
    public User registerUser(String username, String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(password);

        User user = new User();
        user.setUsername(username);
        user.setPassword(hashedPassword);

        return repo.save(user);
    }

    public User findByUsername(String username) {
        Optional<User> user = repo.findByUsername(username);
        if(user.isEmpty())
            return null;
        return user.get();
    }

}
