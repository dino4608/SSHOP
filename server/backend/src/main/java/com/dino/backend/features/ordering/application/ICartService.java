package com.dino.backend.features.ordering.application;

import com.dino.backend.features.ordering.application.model.AddCartItemReq;
import com.dino.backend.features.ordering.application.model.CartItemRes;
import com.dino.backend.features.ordering.application.model.CartRes;
import com.dino.backend.features.ordering.application.model.RemoveCartItemReq;
import com.dino.backend.features.ordering.application.model.UpdateQuantityReq;
import com.dino.backend.shared.api.model.CurrentUser;
import com.dino.backend.shared.application.utils.Deleted;

public interface ICartService {
    // QUERY //

    CartRes get(CurrentUser currentUser);

    // COMMAND //

    CartItemRes addCartItem(AddCartItemReq request, CurrentUser currentUser);

    CartItemRes updateQuantity(UpdateQuantityReq request, CurrentUser currentUser);

    Deleted removeCartItems(RemoveCartItemReq request, CurrentUser currentUser);
}