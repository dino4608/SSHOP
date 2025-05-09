package com.dino.backend.features.identity.application;

import java.time.Instant;

public interface ITokenAppService {

    void updateRefreshToken(String refreshToken, Instant refreshTokenExpiry, String userId);

    boolean isValidRefreshToken(String refreshToken, String userId);
}
