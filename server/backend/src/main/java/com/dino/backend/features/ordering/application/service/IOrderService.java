package com.dino.backend.features.ordering.application.service;

import com.dino.backend.features.ordering.domain.Order;
import com.dino.backend.shared.api.model.CurrentUser;

import java.util.List;

public interface IOrderService {

    List<Order> getOrdersByIds(List<Long> orderIds, CurrentUser currentUser);

    Order changeToPending(Order order);


}