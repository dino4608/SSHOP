package com.dino.backend.features.identity.application.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PasswordLoginRequest {

    // @Pattern(message = "ACCOUNT__EMAIL_NOT_MATCHED", regexp = "^[a-zA-Z0-9.]+@[a-zA-Z0-9.]+\\.[a-zA-Z]{2,}$")
    @Email(message = "AUTH__EMAIL_NOT_MATCHED")
    String email;

    @Size(message = "AUTH__PASSWORD_MIN", min = 6)
    String password;
}
