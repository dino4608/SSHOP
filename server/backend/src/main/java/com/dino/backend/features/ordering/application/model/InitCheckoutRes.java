package com.dino.backend.features.ordering.application.model;

import com.dino.backend.features.ordering.domain.model.CheckoutSnapshot;

import java.util.List;

public record InitCheckoutRes(
        Long checkoutId, // first order id
        CheckoutSnapshot totalCheckoutSnapshot,
        List<OrderRes> orders) {
}
