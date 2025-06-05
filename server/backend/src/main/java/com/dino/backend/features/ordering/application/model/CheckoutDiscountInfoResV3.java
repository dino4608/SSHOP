// CheckoutDiscountInfoRes.java
// Record đại diện cho thông tin chi tiết về giảm giá tổng hợp
package com.dino.backend.features.ordering.application.model;

public record CheckoutDiscountInfoResV3(
        Integer productDiscountOfSeller, // Tổng giảm giá sản phẩm từ người bán (tổng của các đơn hàng)
        Integer couponDiscountOfSeller,  // Tổng giảm giá coupon từ người bán (mặc định 0, tổng của các đơn hàng)
        Integer discountPlatform         // Tổng giảm giá từ nền tảng (mặc định 0, tổng của các đơn hàng)
) {
}