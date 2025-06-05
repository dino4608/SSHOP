// CheckoutSummaryRes.java
// Record đại diện cho các tổng quan chính của toàn bộ giỏ hàng
package com.dino.backend.features.ordering.application.model;

public record CheckoutSummaryResV3(
        Integer totalItemsPrice,    // Tổng tiền hàng (tổng retail price của tất cả các item)
        Integer totalDiscountAmount, // Tổng chiết khấu/giảm giá/tiết kiệm từ tất cả các đơn hàng giả định
        Integer totalShippingFee,   // Tổng phí vận chuyển từ tất cả các đơn hàng giả định
        Integer totalPayableAmount  // Tổng tiền thanh toán cuối cùng
) {
}