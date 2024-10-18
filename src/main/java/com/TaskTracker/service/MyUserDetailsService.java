package com.TaskTracker.service;

import com.TaskTracker.util.MyUserDetails;
import com.TaskTracker.model.User;
import com.TaskTracker.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

//this is used to access user data from the app_users table
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOpt = repo.findByUsername(username);
        if(userOpt.isEmpty())
            throw new UsernameNotFoundException("User not found");
        return new MyUserDetails(userOpt.get());
    }

}
