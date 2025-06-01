package com.dino.backend.features.promotion.application.model;

public record DiscountItemRes(Integer dealPrice, Integer discountPercent) {

    public static DiscountItemRes from(Integer dealPrice, Integer discountPercent) {
        return new DiscountItemRes(dealPrice, discountPercent);
    }

    public static DiscountItemRes from() {
        return null;
    }
}
