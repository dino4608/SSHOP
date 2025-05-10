package com.dino.backend.infrastructure.common;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor //create a constructor for final and @NonNull fields
@FieldDefaults(level = AccessLevel.PUBLIC, makeFinal = true)
@Slf4j
public class Env {
    // JWT //
    @NonFinal
    @Value("${jwt.access.secret-key}")
    String ACCESS_SECRET_KEY;

    @NonFinal
    @Value("${jwt.access.ttl-min}")
    Long ACCESS_TTL_MIN;

    @NonFinal
    @Value("${jwt.refresh.secret-key}")
    String REFRESH_SECRET_KEY;

    @NonFinal
    @Value("${jwt.refresh.ttl-days}")
    Long REFRESH_TTL_DAYS;

    // OAUTH2 GOOGLE //
    @NonFinal
    @Value("${oauth2.google.client-id}")
    String CLIENT_ID;

    @NonFinal
    @Value("${oauth2.google.client-secret}")
    String CLIENT_SECRET;

    @NonFinal
    @Value("${oauth2.google.redirect-uri}")
    String REDIRECT_URI;

    @NonFinal
    @Value("${oauth2.google.grant-type}")
    String GRANT_TYPE;

}
