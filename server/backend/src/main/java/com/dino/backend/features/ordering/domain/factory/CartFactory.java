package com.dino.backend.features.ordering.domain.factory;

import com.dino.backend.features.identity.domain.User;
import com.dino.backend.infrastructure.aop.AppException;
import com.dino.backend.infrastructure.aop.ErrorCode;
import com.dino.backend.features.ordering.domain.Cart;
import com.dino.backend.features.ordering.domain.CartItem;

import java.util.ArrayList;

public class CartFactory {
    public static Cart createCart(String userId) {
        Cart cart = Cart.builder()
                .buyer(User.builder().id(userId).build())
                .items(new ArrayList<>())
                .count(0)
                .build();

        return cart;
    }



    public static CartItem editQuantity(CartItem cartItem) {
        cartItem.setQuantity(genQuantity(cartItem.getQuantity()));

        return cartItem;
    }

    public static CartItem addToCart(CartItem cartItem) {
        cartItem.setQuantity(genQuantity(cartItem.getQuantity()));

        cartItem.getCart().setCount(genCount(cartItem.getCart().getCount()));

        return cartItem;
    }

    private static int genQuantity(int quantity) {
        if (quantity < 1)
            throw new AppException(ErrorCode.CART__QUANTITY_MIN);

        return quantity;
    }

    private static int genCount(int count) {
        if (count >= 100)
            throw new AppException(ErrorCode.CART__COUNT_MAX);

        return ++count;
    }

}
