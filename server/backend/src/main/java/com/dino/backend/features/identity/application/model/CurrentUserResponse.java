package com.dino.backend.features.identity.application.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.ALWAYS)
public class CurrentUserResponse {

    String status;

    String username;

    String email;

    String phone;

    Boolean isEmailVerified;

    Boolean isPhoneVerified;

    String name;

    String photo;
}
