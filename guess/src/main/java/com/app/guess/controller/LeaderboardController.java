package com.app.guess.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.guess.dto.response.ApiResponse;
import com.app.guess.service.LeaderboardService;

import lombok.RequiredArgsConstructor;

import java.util.Set;

import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/leaderboard")
@RequiredArgsConstructor
public class LeaderboardController {
    private final LeaderboardService leaderboardService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<Set<ZSetOperations.TypedTuple<Object>>>> getMethodName(
            @RequestParam(name = "limit", defaultValue = "10") Integer limit) {
        var leaderboard = leaderboardService.getLeaderboard(limit);

        var response = new ApiResponse<>(leaderboard);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
