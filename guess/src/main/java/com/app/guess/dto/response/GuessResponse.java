package com.app.guess.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GuessResponse {
    private boolean correct;

    private int correctNumber;

    private int turns;

    private int score;
}
