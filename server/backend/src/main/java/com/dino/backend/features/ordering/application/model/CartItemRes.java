package com.dino.backend.features.ordering.application.model;

import com.dino.backend.features.promotion.application.model.DiscountItemRes;

import java.util.List;

public record CartItemRes(
        Long id,
        int quantity,
        String photo,
        ProductRes product,
        SkuRes sku,
        DiscountItemRes discountItem) {

    public record ProductRes(
            Long id,
            String name,
            String thumb) {
    }

    public record SkuRes(
            Long id,
            String code,
            List<Integer> tierOptionIndexes,
            String tierOptionValue,
            Integer retailPrice) {
    }
}