package com.app.guess.service.imp;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.guess.constant.ErrorCode;
import com.app.guess.constant.RedisKey;
import com.app.guess.dto.request.GuessRequest;
import com.app.guess.dto.response.GuessResponse;
import com.app.guess.exception.AppException;
import com.app.guess.mapper.UserMapper;
import com.app.guess.model.User;
import com.app.guess.service.GuessService;
import com.app.guess.utils.RandomGuess;
import com.app.guess.utils.UserInfo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GuessServiceImp implements GuessService {
    private final UserMapper userMapper;

    private final RedisTemplate<String, Object> redisTemplate;

    // use transactional to ensure data consistency, and ensure that when user make
    // many requests at the same time, the data is not wrong
    @Override
    @Transactional
    public GuessResponse guess(GuessRequest request) {
        var userId = UserInfo.getCurrentUserId();
        var response = userMapper.getResult(userId);

        if (response.getTurns() <= 0) {
            throw new AppException(ErrorCode.TURNS_LESS_THAN_ONE);
        }

        User user = User.builder().userId(userId).turns(1).score(0).build();
        int randomNumber = RandomGuess.generateRandomNumber(request.getNumber());
        if (request.getNumber() == randomNumber) {
            user.setScore(1);
            response.setScore(response.getScore() + 1);
            redisTemplate.opsForZSet()
                    .add(RedisKey.LEADERBOARD, UserInfo.getCurrentUserName(), response.getScore());

        }

        var update = userMapper.updateScoreAndTurns(user);
        if (update < 1) {
            throw new AppException(ErrorCode.BAD_REQUEST);
        }

        response.setCorrectNumber(randomNumber);
        response.setCorrect(request.getNumber() == randomNumber);
        response.setTurns(response.getTurns() - 1);

        return response;
    }
}
