package com.app.guess.service;

import java.util.Set;

import org.springframework.data.redis.core.ZSetOperations;

public interface LeaderboardService {
    Set<ZSetOperations.TypedTuple<Object>> getLeaderboard(Integer limit);

    void loadLeaderBoard();
}
