package com.dino.backend.features.ordering.application.service;

import com.dino.backend.features.ordering.domain.CartItem;
import com.dino.backend.features.ordering.domain.Order;
import com.dino.backend.features.ordering.domain.OrderItem;
import com.dino.backend.features.shop.domain.Shop;
import com.dino.backend.shared.api.model.CurrentUser;

import java.util.List;

public interface IOrderService {
    // QUERY //

    List<Order> getOrdersByIds(List<Long> orderIds, CurrentUser currentUser);

    // COMMAND //

    List<OrderItem> createOrderItems(List<CartItem> cartItems, CurrentUser currentUser);

    Order createOrder(List<CartItem> cartItems, Shop shop, CurrentUser currentUser);

    Order changeToPending(Order order);


}