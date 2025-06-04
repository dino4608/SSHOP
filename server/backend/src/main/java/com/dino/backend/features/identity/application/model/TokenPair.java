package com.dino.backend.features.identity.application.model;

import java.time.Duration;
import java.time.Instant;

public record TokenPair(
        String accessToken,
        Duration accessTokenTtl,
        Instant accessTokenExpiry,
        String refreshToken,
        Duration refreshTokenTtl,
        Instant refreshTokenExpiry) {
}
