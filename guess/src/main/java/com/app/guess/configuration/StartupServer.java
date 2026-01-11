package com.app.guess.configuration;

import org.springframework.stereotype.Component;

import com.app.guess.service.LeaderboardService;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StartupServer {
    private final LeaderboardService leaderboardService;

    @PostConstruct
    public void appReady() {
        try {
            leaderboardService.loadLeaderBoard();
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

}
