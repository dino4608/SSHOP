package com.dino.backend.features.ordering.application.model;

import com.dino.backend.features.ordering.domain.model.CheckoutSnapshot;

public record EstimateCheckoutRes(Long cartId, CheckoutSnapshot checkoutSnapshot) {
}
