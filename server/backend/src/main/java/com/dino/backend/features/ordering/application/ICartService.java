package com.dino.backend.features.ordering.application;

import com.dino.backend.features.ordering.application.model.CartItemAddReq;
import com.dino.backend.features.ordering.application.model.CartItemAddRes;
import com.dino.backend.features.ordering.application.model.CartItemRemoveReq;
import com.dino.backend.features.ordering.application.model.CartRes;
import com.dino.backend.infrastructure.web.model.CurrentUser;

public interface ICartService {
    // QUERY //

    CartRes get(CurrentUser currentUser);

    // COMMAND //

    CartItemAddRes addCartItem(CartItemAddReq request, CurrentUser currentUser);

    CartItemAddRes updateQuantity(CartItemAddReq request, CurrentUser currentUser);

    void removeCartItems(CartItemRemoveReq request, CurrentUser currentUser);
}