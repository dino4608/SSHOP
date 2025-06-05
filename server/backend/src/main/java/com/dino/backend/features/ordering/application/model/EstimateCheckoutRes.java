package com.dino.backend.features.ordering.application.model;

public record EstimateCheckoutRes(Long cartId, CheckoutSnapshotRes checkoutSnapshot) {
}
