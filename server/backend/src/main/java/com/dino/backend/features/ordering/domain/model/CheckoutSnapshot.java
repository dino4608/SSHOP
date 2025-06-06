package com.dino.backend.features.ordering.domain.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CheckoutSnapshot {
    Summary summary;
    DiscountVoucher discountVoucher;
    ShippingFee shippingFee;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Summary {
        int effectivePrice;
        int savings; // discount voucher + shipping voucher
        int shippingFee;
        int total;   // effective price - discount voucher + final shipping fee.

    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class DiscountVoucher {
        int totalVoucher;
        int sellerVoucher;
        int platformVoucher;

    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class ShippingFee {
        int finalFee;
        int initialFee;
        int sellerShippingVoucher;
        int platformShippingVoucher;

    }
}
