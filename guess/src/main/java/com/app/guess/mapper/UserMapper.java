package com.app.guess.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.app.guess.dto.UserDetailsDto;
import com.app.guess.dto.response.GuessResponse;
import com.app.guess.dto.response.LeaderboardResponse;
import com.app.guess.model.User;

public interface UserMapper {
    UserDetailsDto loadUserbyUsername(@Param("username") String username);

    int signUp(User user);

    boolean existsUsername(@Param("username") String username);

    int updateScoreAndTurns(User user);

    GuessResponse getResult(@Param("userId") String userId);

    List<LeaderboardResponse> getLeaderboard(@Param("limit") int limit);

}
