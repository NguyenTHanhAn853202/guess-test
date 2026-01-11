package com.app.guess.service;

import com.app.guess.dto.response.MeResponse;

import jakarta.servlet.http.HttpServletResponse;

public interface UserService {
    void logout(HttpServletResponse response);

    MeResponse getMe();
}
