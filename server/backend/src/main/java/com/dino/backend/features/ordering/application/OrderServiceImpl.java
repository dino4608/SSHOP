package com.dino.backend.features.ordering.application;

import com.dino.backend.features.identity.domain.User;
import com.dino.backend.features.inventory.application.service.IInventoryService;
import com.dino.backend.features.ordering.application.service.IOrderService;
import com.dino.backend.features.ordering.domain.CartItem;
import com.dino.backend.features.ordering.domain.Order;
import com.dino.backend.features.ordering.domain.OrderItem;
import com.dino.backend.features.ordering.domain.model.CheckoutSnapshot;
import com.dino.backend.features.ordering.domain.model.ShippingDetail;
import com.dino.backend.features.ordering.domain.repository.IOrderRepository;
import com.dino.backend.features.promotion.application.service.IPricingService;
import com.dino.backend.features.shop.domain.Shop;
import com.dino.backend.shared.api.model.CurrentUser;
import com.dino.backend.shared.domain.exception.AppException;
import com.dino.backend.shared.domain.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
@Slf4j
public class OrderServiceImpl implements IOrderService {

    IInventoryService inventoryService;
    IPricingService pricingService;
    IOrderRepository orderRepository;

    // QUERY //

    @Override
    public List<Order> getOrdersByIds(List<Long> orderIds, CurrentUser currentUser) {
        List<Order> orders = this.orderRepository.findByIdIn(orderIds);

        orders.forEach(order -> {
            if (!order.getBuyer().getId().equals(currentUser.id()))
                throw new AppException(ErrorCode.ORDER__NOT_FOUND);
        });
        return orders;
    }

    // COMMAND //

    /*
     * createShippingDetail.
     */
    private ShippingDetail createShippingDetail() {
        var carrier = "Giao hàng tiêu chuẩn";
        var now = Instant.now();
        return ShippingDetail.createShippingDetail(
                carrier,
                now.plus(3, ChronoUnit.DAYS),
                now.plus(5, ChronoUnit.DAYS)
        );
    }

    /*
     * createOrderItem.
     */
    @Override
    public List<OrderItem> createOrderItems(List<CartItem> cartItems, CurrentUser currentUser) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            inventoryService.checkStock(cartItem.getSku().getId(), cartItem.getQuantity());

            var skuPrice = this.pricingService.calculatePrice(cartItem.getSku(), currentUser);

            var order = OrderItem.createOrderItem(cartItem.getSku(), cartItem.getQuantity(), skuPrice.mainPrice());
            orderItems.add(order);
        }
        return orderItems;
    }


    @Override
    public Order createOrder(List<CartItem> cartItems, Shop shop, CurrentUser currentUser) {
        List<OrderItem> orderItems = this.createOrderItems(cartItems, currentUser);

        CheckoutSnapshot checkoutSnapshot = this.pricingService.checkoutOrder(orderItems);
        ShippingDetail shippingDetail = this.createShippingDetail();

        User buyer = currentUser.toUser();

        // create draft Order
        Order order = Order.createDraftOrder(orderItems, buyer, shop, checkoutSnapshot, shippingDetail);
        return orderRepository.save(order);
    }

    @Override
    public Order changeToPending(Order order) {
        order.markAsPending();
        return this.orderRepository.save(order);
    }
}
