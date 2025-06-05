package com.dino.backend.features.ordering.application.model;

import java.util.List;

// Response DTO for API preview checkout
public record CheckoutPreviewRes(
        Long cartId,
        Integer totalItemsPrice,            // Tổng tiền hàng
        Integer totalDiscountAmount,        // Tổng tiết kiệm
        Integer totalShippingFee,           // Tổng phí vận chuyển
        Integer totalPayableAmount,         // Tổng tiền thanh toán

        List<OrderPreviewRes> orders        // List of OrderPreview which group by shop
) {

    public record OrderPreviewRes(
            Long groupId,                   // Likely Id of latest CartItem
            ShopRes shop,
            List<CartItemRes> cartItems,

            // Price or Total
            Integer orderItemsPrice,
            Integer orderDiscountAmount,

            // Discount
            Integer sellerProductDiscount,
            Integer sellerCouponDiscount,
            Integer platformDiscount,        // platformDiscount is only available on first OrderPreview

            // Shipping
            Integer shippingFee,
            Integer initialShippingFee,
            Integer shippingDiscount
    ) {
        public record ShopRes(              // TODO: make ShopRes flat or independent
            Long id,
            String name) {
        }
    }
}
