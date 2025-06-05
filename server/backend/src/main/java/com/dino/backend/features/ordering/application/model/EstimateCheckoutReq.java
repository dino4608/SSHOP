package com.dino.backend.features.ordering.application.model;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record EstimateCheckoutReq(

        @NotEmpty(message = "CHECKOUT__CART_ITEM_IDS_EMPTY")
        List<Long> cartItemIds
) {
}
