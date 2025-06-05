// CheckoutShippingFeeRes.java
// Record đại diện cho thông tin chi tiết về phí vận chuyển tổng hợp
package com.dino.backend.features.ordering.application.model;

public record CheckoutShippingFeeResV3(
        Integer finalShippingFee,   // Tổng phí vận chuyển cuối cùng (tổng của các đơn hàng)
        Integer initialShippingFee, // Tổng phí vận chuyển ban đầu (mặc định 36000/đơn hàng, tổng của các đơn hàng)
        Integer shippingFeeDiscount // Tổng giảm giá phí vận chuyển (mặc định -36000/đơn hàng, tổng của các đơn hàng)
) {
}