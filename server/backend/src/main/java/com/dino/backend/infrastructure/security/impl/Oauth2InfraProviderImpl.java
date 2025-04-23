package com.dino.backend.infrastructure.security.impl;

import com.dino.backend.infrastructure.aop.AppException;
import com.dino.backend.infrastructure.aop.ErrorCode;
import com.dino.backend.infrastructure.security.IOauth2InfraProvider;
import com.dino.backend.infrastructure.security.httpclient.GoogleTokenClient;
import com.dino.backend.infrastructure.security.httpclient.GoogleUserClient;
import com.dino.backend.infrastructure.security.model.GoogleTokenRequest;
import com.dino.backend.infrastructure.security.model.GoogleTokenResponse;
import com.dino.backend.infrastructure.security.model.GoogleUserResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class Oauth2InfraProviderImpl implements IOauth2InfraProvider {

    GoogleTokenClient googleTokenClient;

    GoogleUserClient googleUserClient;

    @NonFinal
    @Value("${oauth2.google.client-id}")
    protected String CLIENT_ID;

    @NonFinal
    @Value("${oauth2.google.client-secret}")
    protected String CLIENT_SECRET;

    @NonFinal
    @Value("${oauth2.google.redirect-uri}")
    protected String REDIRECT_URI;

    @NonFinal
    @Value("${oauth2.google.grant-type}")
    protected String GRANT_TYPE;

    /**
     * @description To exchange AUTHORIZATION_CODE for ACCESS_TOKEN with GOOGLE
     * @param authorizationCode
     * @return accessToken and more
     */
    @Override
    public GoogleTokenResponse getGoogleToken(String authorizationCode) {
        try {
            GoogleTokenResponse googleTokenResponse = this.googleTokenClient.getToken(
                    GoogleTokenRequest.builder()
                            .code(authorizationCode)
                            .clientId(this.CLIENT_ID)
                            .clientSecret(this.CLIENT_SECRET)
                            .redirectUri(this.REDIRECT_URI)
                            .grantType(this.GRANT_TYPE)
                            .build());

            return googleTokenResponse;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new AppException(ErrorCode.OAUTH2__EXCHANGE_WITH_GOOGLE_FAILED);
        }
    }

    /**
     * @description To get information of user of GOOGLE
     * @param accessToken
     * @return user
     */
    @Override
    public GoogleUserResponse getGoogleUser(String accessToken) {
        try {
            GoogleUserResponse googleUserResponse = this.googleUserClient.getUser("json", accessToken);

            return  googleUserResponse;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new AppException(ErrorCode.OAUTH2__GET_GOOGLE_USER_FAILED);
        }
    }
}
