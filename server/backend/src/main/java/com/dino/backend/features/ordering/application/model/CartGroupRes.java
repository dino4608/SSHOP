package com.dino.backend.features.ordering.application.model;

import java.util.List;

public record CartGroupRes(
        Long id,
        ShopRes shop,
        List<CartItemRes> cartItems) {

    public record ShopRes(
            Long id,
            String name) {
    }
}
