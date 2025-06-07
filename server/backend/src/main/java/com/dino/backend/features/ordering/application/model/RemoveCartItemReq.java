package com.dino.backend.features.ordering.application.model;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record RemoveCartItemReq(
        @NotEmpty(message = "CART__ITEMS_EMPTY")
        List<Long> cartItemIds) {
}