package com.app.guess.service.imp;

import java.util.Set;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import com.app.guess.constant.RedisKey;
import com.app.guess.mapper.UserMapper;
import com.app.guess.service.LeaderboardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LeaderBoardServiceImp implements LeaderboardService {

    private final RedisTemplate<String, Object> redisTemplate;

    private final UserMapper userMapper;

    @Override
    public Set<ZSetOperations.TypedTuple<Object>> getLeaderboard(Integer limit) {
        var response = redisTemplate.opsForZSet().reverseRangeWithScores(RedisKey.LEADERBOARD, 0, limit - 1);
        return response;
    }

    @Override
    public void loadLeaderBoard() {
        var leaderboard = userMapper.getLeaderboard(10);

        leaderboard.forEach(
                item -> redisTemplate.opsForZSet().add(RedisKey.LEADERBOARD, item.getUsername(), item.getScore()));
    }
}
