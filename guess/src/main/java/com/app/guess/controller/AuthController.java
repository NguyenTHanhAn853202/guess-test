package com.app.guess.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.guess.dto.request.SignInRequest;
import com.app.guess.dto.request.SignUpRequest;
import com.app.guess.dto.response.ApiResponse;
import com.app.guess.dto.response.SignInResponse;
import com.app.guess.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@Valid @RequestBody SignUpRequest request) {
        authService.signUp(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/sign-in")
    public ResponseEntity<ApiResponse<SignInResponse>> postMethodName(@Valid @RequestBody SignInRequest request,
            HttpServletResponse httpServletResponse) {
        var signIn = authService.signIn(request, httpServletResponse);

        ApiResponse<SignInResponse> response = new ApiResponse<SignInResponse>(signIn);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Void> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        authService.refreshToken(request, response);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
