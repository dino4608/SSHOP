package com.dino.backend.infrastructure.security;

import com.dino.backend.infrastructure.security.model.GoogleTokenResponse;
import com.dino.backend.infrastructure.security.model.GoogleUserResponse;

public interface IOauth2InfraProvider {
    //GOOGLE//
    GoogleTokenResponse getGoogleToken(String code);

    GoogleUserResponse getGoogleUser(String accessToken);
}
