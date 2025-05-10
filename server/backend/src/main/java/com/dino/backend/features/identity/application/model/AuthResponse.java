package com.dino.backend.features.identity.application.model;

import com.dino.backend.features.identity.domain.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.ALWAYS)
public class AuthResponse {

    Boolean isAuthenticated;

    String accessToken;

    CurrentUserResponse currentUser;
}
