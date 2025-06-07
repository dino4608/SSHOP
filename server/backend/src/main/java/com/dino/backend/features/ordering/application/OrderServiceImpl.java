package com.dino.backend.features.ordering.application;

import com.dino.backend.features.ordering.application.service.IOrderService;
import com.dino.backend.features.ordering.domain.Order;
import com.dino.backend.features.ordering.domain.repository.IOrderRepository;
import com.dino.backend.shared.api.model.CurrentUser;
import com.dino.backend.shared.domain.exception.AppException;
import com.dino.backend.shared.domain.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
@Slf4j
public class OrderServiceImpl implements IOrderService {

    IOrderRepository orderRepository;

    @Override
    public List<Order> getOrdersByIds(List<Long> orderIds, CurrentUser currentUser) {
        List<Order> orders = this.orderRepository.findByIdIn(orderIds);

        orders.forEach(order -> {
            if (!order.getBuyer().getId().equals(currentUser.id()))
                throw new AppException(ErrorCode.ORDER__NOT_FOUND);
        });
        return orders;
    }

    @Override
    public Order changeToPending(Order order) {
        order.markAsPending();
        return this.orderRepository.save(order);
    }
}
