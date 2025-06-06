package com.dino.backend.features.ordering.application.model;

import com.dino.backend.features.ordering.domain.model.CheckoutSnapshot;

import java.util.List;

public record InitCheckoutRes(
        Long id, // first order id
        CheckoutSnapshot checkoutSnapshot,
        List<OrderRes> orders) {
}
