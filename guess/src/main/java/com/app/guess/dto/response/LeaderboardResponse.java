package com.app.guess.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeaderboardResponse {
    private String userId;

    private String username;

    private int score;
}
