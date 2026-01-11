package com.app.guess.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.guess.dto.request.GuessRequest;
import com.app.guess.dto.response.ApiResponse;
import com.app.guess.dto.response.GuessResponse;
import com.app.guess.service.GuessService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/guess")
@RequiredArgsConstructor
public class GuessController {
    private final GuessService guessService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<GuessResponse>> guess(@Valid @RequestBody GuessRequest request) {
        GuessResponse response = guessService.guess(request);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(response));
    }
}
