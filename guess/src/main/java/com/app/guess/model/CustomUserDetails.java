package com.app.guess.model;

import java.util.Collection;
import java.util.Collections;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;

@Builder
public class CustomUserDetails implements UserDetails {
    private String username;

    private String password;

    private String userId;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Dont have role;
        return Collections.emptyList();
    }

    @Override
    public @Nullable String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public String getUserId() {
        return userId;
    }

}
