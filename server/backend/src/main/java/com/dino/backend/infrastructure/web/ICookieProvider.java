package com.dino.backend.infrastructure.web;

import org.springframework.http.HttpHeaders;

import java.time.Duration;

public interface ICookieProvider {
    void attachRefreshToken(HttpHeaders headers, String refreshToken, Duration ttl);
    void clearRefreshToken(HttpHeaders headers);
}
