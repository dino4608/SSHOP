package com.dino.backend.features.identity.application.provider;

import com.dino.backend.features.identity.application.model.GoogleUserResponse;

public interface IIdentityOauth2Provider {

    GoogleUserResponse authViaGoogle(String code);
}
