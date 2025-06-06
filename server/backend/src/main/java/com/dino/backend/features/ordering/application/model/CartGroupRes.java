package com.dino.backend.features.ordering.application.model;

import com.dino.backend.features.shop.application.model.ShopLean;

import java.util.List;

public record CartGroupRes(
        Long id,
        ShopLean shop,
        List<CartItemRes> cartItems) {
}
