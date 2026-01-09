package com.app.guess.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @PostMapping("/sign-up")
    public ResponseEntity<Void> postMethodName(@RequestBody String entity) {

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
