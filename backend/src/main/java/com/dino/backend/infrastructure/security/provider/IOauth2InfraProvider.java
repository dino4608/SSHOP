package com.dino.backend.infrastructure.security.provider;

import com.dino.backend.features.identity.domain.entity.Account;
import com.dino.backend.infrastructure.security.model.enums.Oauth2Type;
import com.dino.backend.infrastructure.security.model.response.ExchangeTokenResponse;

public interface IOauth2InfraProvider {
    //GOOGLE//
    ExchangeTokenResponse exchangeToken(String code, Oauth2Type oauth2Type);

    Account getUserInfo(String accessToken, Oauth2Type oauth2Type);
}
