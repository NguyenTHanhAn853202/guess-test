package com.app.guess.mapper;

import org.apache.ibatis.annotations.Param;

import com.app.guess.dto.UserDetailsDto;

public interface UserMapper {
    UserDetailsDto loadUserbyUsername(@Param("username") String username);
}
