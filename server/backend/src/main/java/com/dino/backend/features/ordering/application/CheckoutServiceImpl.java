package com.dino.backend.features.ordering.application;

import com.dino.backend.features.identity.domain.User;
import com.dino.backend.features.inventory.application.service.IInventoryService;
import com.dino.backend.features.ordering.application.mapper.ICheckoutMapper;
import com.dino.backend.features.ordering.application.mapper.IOrderMapper;
import com.dino.backend.features.ordering.application.model.*;
import com.dino.backend.features.ordering.application.service.ICartService;
import com.dino.backend.features.ordering.application.service.ICheckoutService;
import com.dino.backend.features.ordering.application.service.IOrderService;
import com.dino.backend.features.ordering.domain.Cart;
import com.dino.backend.features.ordering.domain.CartItem;
import com.dino.backend.features.ordering.domain.Order;
import com.dino.backend.features.ordering.domain.OrderItem;
import com.dino.backend.features.ordering.domain.model.CheckoutSnapshot;
import com.dino.backend.features.ordering.domain.model.ShippingDetail;
import com.dino.backend.features.ordering.domain.repository.ICartRepository;
import com.dino.backend.features.ordering.domain.repository.IOrderRepository;
import com.dino.backend.features.promotion.application.model.DiscountItemRes;
import com.dino.backend.features.promotion.application.service.IDiscountService;
import com.dino.backend.features.promotion.application.service.IPricingService;
import com.dino.backend.features.shop.domain.Shop;
import com.dino.backend.shared.api.model.CurrentUser;
import com.dino.backend.shared.domain.exception.AppException;
import com.dino.backend.shared.domain.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CheckoutServiceImpl implements ICheckoutService {

    IPricingService pricingService;
    IDiscountService discountService;
    IInventoryService inventoryService;
    ICartService cartService;
    IOrderService orderService;
    ICartRepository cartRepository;
    IOrderRepository orderRepository;
    ICheckoutMapper checkoutMapper;
    IOrderMapper orderMapper;

    private static final int DEFAULT_SHOP_DISCOUNT_VOUCHER = 0;
    private static final int DEFAULT_PLATFORM_DISCOUNT_VOUCHER = 0;
    private static final int DEFAULT_INITIAL_SHIPPING_FEE = 36000;
    private static final int DEFAULT_SHOP_SHIPPING_VOUCHER = 0;
    private static final int DEFAULT_PLATFORM_SHIPPING_VOUCHER = 36000;

    // DOMAIN //

    /**
     * createOrderItem.
     */
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

    /**
     * calculateCheckout.
     */
    private CheckoutSnapshot calculateCheckout(List<OrderItem> orderItems) {
        int totalEffectivePrice = orderItems.stream()
                .mapToInt(OrderItem::getMainPrice)
                .sum();

        int shopDiscountVoucher = DEFAULT_SHOP_DISCOUNT_VOUCHER;
        int platformDiscountVoucher = DEFAULT_PLATFORM_DISCOUNT_VOUCHER;

        int initialShippingFee = DEFAULT_INITIAL_SHIPPING_FEE;
        int shopShippingVoucher = DEFAULT_SHOP_SHIPPING_VOUCHER;
        int platformShippingVoucher = DEFAULT_PLATFORM_SHIPPING_VOUCHER;

        // 5. calculate final totals
        int totalDiscountVoucher = shopDiscountVoucher + platformDiscountVoucher;
        int totalShippingVoucher = shopShippingVoucher + platformShippingVoucher;
        int totalSavings = totalDiscountVoucher + totalShippingVoucher;
        int finalShippingFee = Math.max(0, initialShippingFee - totalShippingVoucher);
        int finalTotal = Math.max(0, totalEffectivePrice - totalDiscountVoucher + finalShippingFee);


        // 6. response dto
        CheckoutSnapshot.Summary summary = checkoutMapper.toSummary(
                totalEffectivePrice, totalSavings, finalShippingFee, finalTotal);

        CheckoutSnapshot.DiscountVoucher discountVoucher = checkoutMapper.toDiscountVoucher(
                totalDiscountVoucher, shopDiscountVoucher, platformDiscountVoucher);

        CheckoutSnapshot.ShippingFee shippingFee = checkoutMapper.toShippingFee(
                finalShippingFee, initialShippingFee, shopShippingVoucher, platformShippingVoucher);

        return checkoutMapper.toCheckoutSnapshot(
                summary, discountVoucher, shippingFee);
    }

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

    // QUERY //

    /**
     * estimateCheckout
     */
    @Override
    public EstimateCheckoutRes estimateCheckout(EstimateCheckoutReq request, CurrentUser currentUser) {
        // 1. get Cart with essential relationships
        Cart cart = cartRepository.findWithShopByBuyerId(currentUser.id())
                .orElseThrow(() -> new AppException(ErrorCode.CART__NOT_FOUND));

        // 2. filter target CartItems
        List<CartItem> selectedCartItems = cart.getCartItems().stream()
                .filter(item -> request.cartItemIds().contains(item.getId()))
                .toList();

        if (selectedCartItems.isEmpty()) {
            throw new AppException(ErrorCode.CART__ITEMS_EMPTY);
        }

        // 3. group CartItems by Shop. every group will be a future Order.
        Map<Shop, List<CartItem>> itemsGroupedByShop = selectedCartItems.stream()
                .collect(Collectors.groupingBy(item -> item.getSku().getProduct().getShop()));

        // init total values in PreviewCheckoutRes
        int totalItemsPrice = 0;

        int totalSellerDiscount = 0;
        int totalSellerCoupon = 0;
        int totalPlatformDiscount = 0;
        int totalPlatformCoupon = 0;

        int totalInitialShippingFee = 0;
        int totalSellerShippingPromotion = 0;
        int totalPlatformShippingPromotion = 0;


        // 4. iterate each Order compute total contributions
        for (Map.Entry<Shop, List<CartItem>> entry : itemsGroupedByShop.entrySet()) {
            List<CartItem> cartItemsInOrder = entry.getValue();

            int currentOrderItemsPrice = 0;
            int currentOrderDiscount = 0;

            for (CartItem cartItem : cartItemsInOrder) {
                DiscountItemRes discountItemRes = this.discountService.canDiscountAndCalculate(cartItem.getSku(), currentUser);

                int itemRetailPrice = cartItem.getSku().getRetailPrice() * cartItem.getQuantity();
                int itemDealPrice = itemRetailPrice;

                if (discountItemRes != null && discountItemRes.dealPrice() != null) {
                    itemDealPrice = discountItemRes.dealPrice() * cartItem.getQuantity();
                }

                currentOrderItemsPrice += itemRetailPrice;
                currentOrderDiscount += (itemRetailPrice - itemDealPrice);
            }

            totalItemsPrice += currentOrderItemsPrice;

            totalSellerDiscount += currentOrderDiscount;
            totalSellerCoupon += DEFAULT_SHOP_DISCOUNT_VOUCHER;
            totalPlatformDiscount += 0;
            totalPlatformCoupon += DEFAULT_PLATFORM_DISCOUNT_VOUCHER;

            totalInitialShippingFee += DEFAULT_INITIAL_SHIPPING_FEE;
            totalSellerShippingPromotion += DEFAULT_SHOP_SHIPPING_VOUCHER;
            totalPlatformShippingPromotion += DEFAULT_PLATFORM_SHIPPING_VOUCHER;
        }

        // 5. compute latest totals

        // PricePromotionRes.totalAmount
        int pricePromotionAmount = totalSellerDiscount + totalSellerCoupon +
                totalPlatformDiscount + totalPlatformCoupon;

        // ShippingFeeRes.finalFee
        int finalShippingFee = Math.max(0, totalInitialShippingFee -
                totalSellerShippingPromotion - totalPlatformShippingPromotion); // ensure no negative

        // SummaryRes.promotionAmount
        Integer totalPromotionAmount = pricePromotionAmount + (totalInitialShippingFee - finalShippingFee);


        // SummaryRes.payableAmount
        Integer payableAmount = totalItemsPrice - pricePromotionAmount + finalShippingFee;


        // 6. response dto
        CheckoutSnapshotRes.SummaryRes summary = checkoutMapper.toSummaryRes(
                totalItemsPrice,
                totalPromotionAmount,
                finalShippingFee,
                payableAmount
        );

        CheckoutSnapshotRes.PricePromotionRes pricePromotion = checkoutMapper.toPricePromotionRes(
                pricePromotionAmount,
                totalSellerDiscount,
                totalSellerCoupon,
                totalPlatformDiscount,
                totalPlatformCoupon
        );

        CheckoutSnapshotRes.ShippingFeeRes shippingFee = checkoutMapper.toShippingFeeRes(
                finalShippingFee,
                totalInitialShippingFee,
                totalSellerShippingPromotion,
                totalPlatformShippingPromotion
        );

        CheckoutSnapshotRes checkoutSnapshot = checkoutMapper.toCheckoutSnapshotRes(
                summary,
                pricePromotion,
                shippingFee
        );

        return checkoutMapper.toEstimateCheckoutRes(
                cart.getId(),
                checkoutSnapshot
        );
    }



    // COMMAND //

    /**
     * initCheckout
     */
    @Override
    @Transactional
    public InitCheckoutRes initCheckout(InitCheckoutReq request, CurrentUser currentUser) {
        // 1. get Cart, group CartItems by Shop
        Cart cart = this.cartService.getCartWithShop(currentUser);

        var itemsGroupedByShop = this.cartService.groupCartItemByShop(cart, request.cartItemIds())
                .orElseThrow(() -> new AppException(ErrorCode.CART__NOT_FOUND));

        // 2. create draft Orders
        List<OrderRes> ordersRes = new ArrayList<>();
        CheckoutSnapshot totalCheckoutSnapshot = CheckoutSnapshot.empty();

        for (var entry : itemsGroupedByShop.entrySet()) {
            Shop shop = entry.getKey();
            List<CartItem> cartItems = entry.getValue();

            // create OrderItems
            List<OrderItem> orderItems = this.createOrderItems(cartItems, currentUser);

            CheckoutSnapshot checkoutSnapshot = this.calculateCheckout(orderItems);

            ShippingDetail shippingDetail = this.createShippingDetail();

            User buyer = currentUser.toUser();

            Order order = Order.createDraftOrder(orderItems, buyer, shop, checkoutSnapshot, shippingDetail);
            Order createdOrder = orderRepository.save(order);

            // map to response
            ordersRes.add(this.orderMapper.toOrderRes(createdOrder));
            totalCheckoutSnapshot.accumulateFrom(createdOrder.getCheckoutSnapshot());
        }

        return this.checkoutMapper.toInitCheckoutRes(ordersRes.getFirst().id(), totalCheckoutSnapshot, ordersRes);
    }

    @Override
    @Transactional
    public ConfirmCheckoutRes confirmCheckout(ConfirmCheckoutReq request, CurrentUser currentUser) {
        // 1. get orders of current user
        List<Order> ordersToConfirm = this.orderService.getOrdersByIds(request.orderIds(), currentUser);

        // 2. change status to PENDING
        List<Order> updatedOrders = new ArrayList<>();
        for (Order order : ordersToConfirm) {
            // reserve stock
            for (OrderItem orderItem : order.getOrderItems()) {
                inventoryService.reserveStock(orderItem.getSku().getId(), orderItem.getQuantity());
            }
            Order updatedOrder = this.orderService.changeToPending(order);
            updatedOrders.add(updatedOrder);
        }

        return ConfirmCheckoutRes.success(updatedOrders.size());
    }
}