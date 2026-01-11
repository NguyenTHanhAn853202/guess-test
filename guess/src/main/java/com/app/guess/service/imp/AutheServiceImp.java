package com.app.guess.service.imp;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.guess.constant.ErrorCode;
import com.app.guess.constant.TokenName;
import com.app.guess.dto.request.SignInRequest;
import com.app.guess.dto.request.SignUpRequest;
import com.app.guess.dto.response.SignInResponse;
import com.app.guess.exception.AppException;
import com.app.guess.mapper.UserMapper;
import com.app.guess.model.CustomUserDetails;
import com.app.guess.model.User;
import com.app.guess.service.AuthService;
import com.app.guess.utils.CookieUtils;
import com.app.guess.utils.JwtUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AutheServiceImp implements AuthService {
    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    @Value("${jwt.access-token-expiration}")
    private int maxAgeAccessToken; // 15 minutes

    @Value("${jwt.refresh-token-expiration}")
    private int maxAgeRefreshToken; // 7 days

    @Override
    public void signUp(SignUpRequest request) {
        if (userMapper.existsUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USERNAME_EXISTS);
        }

        var userId = UUID.randomUUID().toString();
        var encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = User.builder().userId(userId).username(request.getUsername()).password(encodedPassword).build();

        int result = userMapper.signUp(user);

        if (result < 1) {
            throw new AppException(ErrorCode.BAD_REQUEST);
        }
    }

    @Override
    public SignInResponse signIn(SignInRequest request, HttpServletResponse httpServletResponse) {
        var userDetails = (CustomUserDetails) authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()))
                .getPrincipal();

        String accessToken = jwtUtils.generateAcessToken(userDetails.getUsername());
        String refreshToken = jwtUtils.generateRefreshToken(userDetails.getUsername());

        var accessTokenCookie = CookieUtils.createHttpOnlyCookie(TokenName.ACESS_TOKEN, accessToken, maxAgeAccessToken);
        var refreshTokenCookie = CookieUtils.createHttpOnlyCookie(TokenName.REFRESH_TOKEN, refreshToken,
                maxAgeRefreshToken);

        httpServletResponse.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie);
        httpServletResponse.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie);

        return SignInResponse.builder().userId(userDetails.getUserId()).username(userDetails.getUsername()).build();
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        var refreshToken = CookieUtils.getTokenFromCookie(request, TokenName.REFRESH_TOKEN);
        if (!jwtUtils.isRefreshTokenValid(refreshToken)) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        var username = jwtUtils.getRefreshTokenUsername(refreshToken);

        var accessToken = jwtUtils.generateAcessToken(username);
        var newRefreshToken = jwtUtils.generateRefreshToken(username);

        var accessTokenCookie = CookieUtils.createHttpOnlyCookie(TokenName.ACESS_TOKEN, accessToken, maxAgeAccessToken);
        var refreshTokenCookie = CookieUtils.createHttpOnlyCookie(TokenName.REFRESH_TOKEN, newRefreshToken,
                maxAgeRefreshToken);

        response.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie);
        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie);

    }
}
