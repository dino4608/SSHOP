package com.dino.backend.features.identity.application;

import com.dino.backend.features.identity.domain.Token;

import java.time.Instant;

public interface ITokenAppService {

    Token getById(String userId);

    void updateRefreshToken(String refreshToken, Instant refreshTokenExpiry, String userId);

    boolean isRefreshTokenValid(String refreshToken, String userId);
}
