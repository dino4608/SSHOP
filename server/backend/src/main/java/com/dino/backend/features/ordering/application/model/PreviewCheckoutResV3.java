// PreviewCheckoutRes.java
// Response DTO cho API preview checkout, chứa tất cả các tổng hợp
package com.dino.backend.features.ordering.application.model;

public record PreviewCheckoutResV3(
        Long cartId,                 // ID của giỏ hàng gốc
        CheckoutSummaryResV3 summary,      // Tổng quan chính
        CheckoutDiscountInfoResV3 discountInfo, // Chi tiết giảm giá
        CheckoutShippingFeeResV3 shippingFee    // Chi tiết phí vận chuyển
) {
}