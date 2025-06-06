package com.dino.backend.features.promotion.application.service;

import com.dino.backend.features.ordering.domain.CartItem;
import com.dino.backend.features.ordering.domain.OrderItem;
import com.dino.backend.features.ordering.domain.model.CheckoutSnapshot;
import com.dino.backend.features.productcatalog.domain.Sku;
import com.dino.backend.features.promotion.application.model.SkuPrice;
import com.dino.backend.features.promotion.domain.Discount;
import com.dino.backend.shared.api.model.CurrentUser;

import java.util.List;

public interface IPricingService {

    SkuPrice calculateRetail(Sku sku);

    SkuPrice calculateDiscount(Sku sku, Discount discount);

    SkuPrice calculatePrice(Sku sku, CurrentUser currentUser);

    CheckoutSnapshot checkoutOrder(List<OrderItem> orderItems);

    CheckoutSnapshot checkoutCartGroup(List<CartItem> cartItems, CurrentUser currentUser);
}
