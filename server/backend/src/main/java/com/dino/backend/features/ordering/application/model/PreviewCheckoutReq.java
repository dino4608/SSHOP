package com.dino.backend.features.ordering.application.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

// Request DTO for API preview checkout
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PreviewCheckoutReq {

    @NotEmpty(message = "CHECKOUT__CART_ITEM_IDS_EMPTY")
    List<Long> cartItemIds;
}
