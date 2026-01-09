package com.app.guess.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User extends BaseEntity {
    private String userId;

    private String username;

    private String password;

    private int score;

    private int turns;
}
