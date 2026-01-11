package com.app.guess.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MeResponse {
    private String username;

    private int score;

    private int turns;
}
