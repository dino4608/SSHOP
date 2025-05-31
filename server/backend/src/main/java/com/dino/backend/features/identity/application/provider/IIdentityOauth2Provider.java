package com.dino.backend.features.identity.application.provider;

import com.dino.backend.infrastructure.security.model.GoogleTokenResponse;
import com.dino.backend.infrastructure.security.model.GoogleUserResponse;

public interface IIdentityOauth2Provider {
    // GOOGLE//
    GoogleTokenResponse getGoogleToken(String code);

    GoogleUserResponse getGoogleUser(String accessToken);
}
