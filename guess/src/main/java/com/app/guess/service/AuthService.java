package com.app.guess.service;

import com.app.guess.dto.request.SignInRequest;
import com.app.guess.dto.request.SignUpRequest;
import com.app.guess.dto.response.SignInResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    public void signUp(SignUpRequest request);

    public SignInResponse signIn(SignInRequest request, HttpServletResponse httpServletResponse);

    public void refreshToken(HttpServletRequest request, HttpServletResponse response);
}
