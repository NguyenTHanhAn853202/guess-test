package com.app.guess.utils;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
    private final Key ACCESS_TOKEN_SECRET;

    private final Key REFRESH_TOKEN_SECRET;

    private final long ACCESS_TOKEN_EXPIRATION_TIME;

    private final long REFRESH_TOKEN_EXPIRATION_TIME;

    public JwtUtils(
            @Value("${jwt.access-token-key}") String accessTokenSecret,
            @Value("${jwt.refresh-token-key}") String refreshTokenSecret,
            @Value("${jwt.access-token-expiration}") long accessTokenExpiration,
            @Value("${jwt.refresh-token-expiration}") long refreshTokenExpiration) {
        this.ACCESS_TOKEN_SECRET = hashKey(accessTokenSecret);
        this.REFRESH_TOKEN_SECRET = hashKey(refreshTokenSecret);
        this.ACCESS_TOKEN_EXPIRATION_TIME = accessTokenExpiration;
        this.REFRESH_TOKEN_EXPIRATION_TIME = refreshTokenExpiration;
    }

    public String generateAcessToken(String username) {
        return generateToken(username, ACCESS_TOKEN_SECRET, ACCESS_TOKEN_EXPIRATION_TIME);
    }

    public String generateRefreshToken(String username) {
        return generateToken(username, REFRESH_TOKEN_SECRET, REFRESH_TOKEN_EXPIRATION_TIME);
    }

    public String getAccessTokenUsername(String token) {
        return getClaims(token, ACCESS_TOKEN_SECRET).getSubject();
    }

    public String getRefreshTokenUsername(String token) {
        return getClaims(token, REFRESH_TOKEN_SECRET).getSubject();
    }

    public boolean isAccessTokenValid(String token) {
        return isValidToken(token, ACCESS_TOKEN_SECRET);
    }

    public boolean isRefreshTokenValid(String token) {
        return isValidToken(token, REFRESH_TOKEN_SECRET);
    }

    private boolean isValidToken(String token, Key key) {
        try {
            getClaims(token, key);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaims(String token, Key key) {
        return Jwts.parser().verifyWith((SecretKey) key).build().parseSignedClaims(token).getPayload();
    }

    private Key hashKey(String key) {
        return Keys.hmacShaKeyFor(key.getBytes());
    }

    private String generateToken(String username, Key key, long EXPIRATION_TIME) {
        return Jwts.builder().subject(username).issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)).signWith(key).compact();
    }
}
