package com.dino.backend.features.identity.application.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LookupIdentifierRequest {

    Boolean isEmailProvided;

    Boolean isPasswordProvided;
}
