package com.app.guess.service.imp;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.app.guess.constant.TokenName;
import com.app.guess.dto.response.MeResponse;
import com.app.guess.mapper.UserMapper;
import com.app.guess.service.UserService;
import com.app.guess.utils.CookieUtils;
import com.app.guess.utils.UserInfo;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserMapper userMapper;

    @Override
    public void logout(HttpServletResponse response) {
        var removeCookieAccessToken = CookieUtils.createHttpOnlyCookie(TokenName.ACESS_TOKEN, null, 0);
        var removeCookieRefreshToken = CookieUtils.createHttpOnlyCookie(TokenName.REFRESH_TOKEN, null, 0);
        response.addHeader(HttpHeaders.SET_COOKIE, removeCookieRefreshToken);
        response.addHeader(HttpHeaders.SET_COOKIE, removeCookieAccessToken);
    }

    @Override
    public MeResponse getMe() {
        String username = UserInfo.getCurrentUserName();
        String userId = UserInfo.getCurrentUserId();
        var userInfo = userMapper.getResult(userId);

        var me = MeResponse.builder().username(username).score(userInfo.getScore()).turns(userInfo.getTurns()).build();

        return me;
    }
}
