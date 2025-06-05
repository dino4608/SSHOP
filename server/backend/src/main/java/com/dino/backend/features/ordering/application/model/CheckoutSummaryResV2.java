package com.dino.backend.features.ordering.application.model;

public record CheckoutSummaryResV2(
        Integer totalItemsPrice,            // sum retailPrice
        Integer totalDiscountAmount,        // by Discount, Coupon, or Platform
        Integer totalShippingFee,
        Integer totalPayableAmount
) {
}
