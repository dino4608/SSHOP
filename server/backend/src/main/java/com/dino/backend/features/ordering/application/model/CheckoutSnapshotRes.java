package com.dino.backend.features.ordering.application.model;

public record CheckoutSnapshotRes(
        SummaryRes summary,
        PricePromotionRes pricePromotion,
        ShippingFeeRes shippingFee
) {
    public record SummaryRes(
            Integer itemsPrice,
            Integer promotionAmount,// discount + coupon + shipping promotion
            Integer shippingFee,
            Integer payableAmount   // itemsPrice - PricePromotionRes.total + ShippingFeeRes.final
    ) {
    }

    public record PricePromotionRes(
            Integer totalAmount,
            Integer sellerDiscount,
            Integer sellerCoupon,
            Integer platformDiscount,
            Integer platformCoupon
    ) {
    }

    public record ShippingFeeRes(
            Integer finalFee,
            Integer initialFee,
            Integer sellerShippingPromotion,
            Integer platformShippingPromotion
    ) {
    }
}