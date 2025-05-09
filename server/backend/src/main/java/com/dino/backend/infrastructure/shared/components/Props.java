package com.dino.backend.infrastructure.shared.components;

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
public class Props {

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

}
