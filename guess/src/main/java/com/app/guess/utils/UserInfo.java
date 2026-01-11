package com.app.guess.utils;

import org.springframework.security.core.context.SecurityContextHolder;

import com.app.guess.model.CustomUserDetails;

public class UserInfo {
    public static String getCurrentUserName() {
        var user = getCurrentUserDetails();
        return user.getUsername();
    }

    public static String getCurrentUserId() {
        var user = getCurrentUserDetails();
        return user.getUserId();
    }

    private static CustomUserDetails getCurrentUserDetails() {
        return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
