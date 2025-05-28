package com.dino.backend.features.ordering.application.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

// DTO này tương tự CartItemRes nhưng có thể bổ sung thêm thông tin
// nếu cần đặc trưng cho kết quả của thao tác "thêm vào giỏ hàng"
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemAddRes { // Đặt tên là CartItemAddedRes để phân biệt rõ ràng

    String id; // CartItem ID
    String skuId;
    String skuCode;
    String skuTierOptionValue;
    Integer retailPrice;
    int quantity; // Số lượng CẬP NHẬT sau khi thêm
    String productName;
    String productThumb;

    // Có thể thêm các trường khác nếu cần, ví dụ:
    // String message; // "Item added successfully" or "Quantity updated"
    // int currentCartTotalItems; // Tổng số item hiện tại trong giỏ hàng
}