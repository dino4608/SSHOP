package com.dino.backend.infrastructure.security;

import com.dino.backend.features.identity.domain.User;
import com.dino.backend.infrastructure.security.model.JwtType;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

public interface ISecurityInfraProvider {
    //PASSWORD//
    String hashPassword(String plain);

    boolean matchPassword(String plain, String hash);

    //JWT//
    byte[] getSecretKey(JwtType jwtType);

    Duration getTtl(JwtType jwtType);

    Instant getExpiry(JwtType jwtType);

    String genToken(User account, JwtType jwtType);

    Optional<String> verifyToken(String token, JwtType jwtType);
}
