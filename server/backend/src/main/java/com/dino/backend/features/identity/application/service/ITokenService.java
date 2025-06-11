package com.dino.backend.features.identity.application.service;

import java.time.Instant;

import com.dino.backend.features.identity.domain.Token;

public interface ITokenService {

    Token getByUserId(Long userId);

    void updateRefreshToken(String refreshToken, Instant refreshTokenExpiry, Long userId);

    boolean isRefreshTokenValid(String refreshToken, Long userId);
}
