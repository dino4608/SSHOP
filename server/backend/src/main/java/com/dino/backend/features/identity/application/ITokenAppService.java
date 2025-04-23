package com.dino.backend.features.identity.application;

import java.time.Instant;

public interface ITokenAppService {

    void updateRefreshToken(String REFRESH_TOKEN, Instant refreshExpDate, String userId);
}
