package com.dino.backend.shared.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageReq {
    @Builder.Default
    int page = 1;

    @Builder.Default
    int size = 4;

    @Builder.Default
    String sort = "updatedAt";

    @Builder.Default
    String direct = "desc";
}
