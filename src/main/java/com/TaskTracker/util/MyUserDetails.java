package com.TaskTracker.util;

import com.TaskTracker.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@AllArgsConstructor
public class MyUserDetails implements UserDetails {

    private User user;

    //not necessary since this project doesn't have user roles, but the method still needs to be overridden / implemented
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    //these also need to be overridden / implemented
    @Override
    public String getPassword() {
        return user.getPassword();
    }
    @Override
    public String getUsername() {
        return user.getUsername();
    }
}
