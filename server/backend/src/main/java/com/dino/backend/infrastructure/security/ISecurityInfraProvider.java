package com.dino.backend.infrastructure.security;

import com.dino.backend.features.identity.domain.User;
import com.dino.backend.infrastructure.security.model.JwtType;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Duration;
import java.time.Instant;

public interface ISecurityInfraProvider {
    //PASSWORD//
    String hashPassword(String plain);

    boolean matchPassword(String plain, String hash);

    //JWT//
    String genToken(User account, JwtType tokenType);

    Jwt decodeToken(String token, JwtType tokenType);

    Duration getValidDuration(JwtType tokenType);

    Instant getExpirationDate(JwtType tokenType);

    String getSecretKey(JwtType tokenType);

    String getAccountId();
}
