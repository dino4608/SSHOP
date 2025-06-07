package com.dino.backend.features.ordering.domain.model;

import com.dino.backend.features.ordering.domain.Order;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CheckoutSnapshot {

    // NESTED OBJECTS //

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

    // PROPS //

    Summary summary;
    DiscountVoucher discountVoucher;
    ShippingFee shippingFee;

    // FACTORY METHODS //

    public static CheckoutSnapshot empty() {
        return CheckoutSnapshot.builder()
                .summary(new Summary())
                .discountVoucher(new DiscountVoucher())
                .shippingFee(new ShippingFee())
                .build();
    }

    // INSTANCE METHODS //

    public void accumulateFrom(CheckoutSnapshot other) {
        if (other == null) return;

        if (other.getSummary() != null) {
            // if (this.summary == null) this.summary = new Summary();
            this.summary.setEffectivePrice(this.summary.getEffectivePrice() + other.getSummary().getEffectivePrice());
            this.summary.setSavings(this.summary.getSavings() + other.getSummary().getSavings());
            this.summary.setShippingFee(this.summary.getShippingFee() + other.getSummary().getShippingFee());
            this.summary.setTotal(this.summary.getTotal() + other.getSummary().getTotal());
        }

        if (other.getDiscountVoucher() != null) {
            // if (this.discountVoucher == null) this.discountVoucher = new DiscountVoucher();
            this.discountVoucher.setTotalVoucher(this.discountVoucher.getTotalVoucher() + other.getDiscountVoucher().getTotalVoucher());
            this.discountVoucher.setSellerVoucher(this.discountVoucher.getSellerVoucher() + other.getDiscountVoucher().getSellerVoucher());
            this.discountVoucher.setPlatformVoucher(this.discountVoucher.getPlatformVoucher() + other.getDiscountVoucher().getPlatformVoucher());
        }

        if (other.getShippingFee() != null) {
            // if (this.shippingFee == null) this.shippingFee = new ShippingFee();
            this.shippingFee.setFinalFee(this.shippingFee.getFinalFee() + other.getShippingFee().getFinalFee());
            this.shippingFee.setInitialFee(this.shippingFee.getInitialFee() + other.getShippingFee().getInitialFee());
            this.shippingFee.setSellerShippingVoucher(this.shippingFee.getSellerShippingVoucher() + other.getShippingFee().getSellerShippingVoucher());
            this.shippingFee.setPlatformShippingVoucher(this.shippingFee.getPlatformShippingVoucher() + other.getShippingFee().getPlatformShippingVoucher());
        }
    }
}
