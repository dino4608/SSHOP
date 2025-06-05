package com.dino.backend.features.ordering.application.impl;

import com.dino.backend.features.ordering.application.ICheckoutService;
import com.dino.backend.features.ordering.application.mapper.ICheckoutMapper;
import com.dino.backend.features.ordering.application.model.EstimateCheckoutReq;
import com.dino.backend.features.ordering.application.model.CheckoutSnapshotRes;
import com.dino.backend.features.ordering.application.model.EstimateCheckoutRes;
import com.dino.backend.features.ordering.domain.Cart;
import com.dino.backend.features.ordering.domain.CartItem;
import com.dino.backend.features.ordering.domain.repository.ICartRepository;
import com.dino.backend.features.promotion.application.IDiscountService;
import com.dino.backend.features.promotion.application.model.DiscountItemRes;
import com.dino.backend.features.shop.domain.Shop;
import com.dino.backend.shared.api.model.CurrentUser;
import com.dino.backend.shared.domain.exception.AppException;
import com.dino.backend.shared.domain.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CheckoutServiceImpl implements ICheckoutService {

    IDiscountService discountService;
    ICartRepository cartRepository;
    ICheckoutMapper checkoutMapper;

    private static final int DEFAULT_PLATFORM_DISCOUNT_PROMOTION = 0;
    private static final int DEFAULT_SELLER_COUPON_PROMOTION = 0;
    private static final int DEFAULT_PLATFORM_COUPON_PROMOTION = 0;
    private static final int DEFAULT_INITIAL_SHIPPING_FEE = 36000;
    private static final int DEFAULT_SELLER_SHIPPING_PROMOTION = 0;
    private static final int DEFAULT_PLATFORM_SHIPPING_PROMOTION = 36000;

    // QUERY //

    /**
     * previewCheckout
     * (preview checkout of CartItem list)
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
            throw new AppException(ErrorCode.CHECKOUT__NO_SELECTED_ITEMS);
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
                DiscountItemRes discountItemRes = this.discountService.canDiscount(cartItem.getSku(), currentUser);

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
            totalSellerCoupon += DEFAULT_SELLER_COUPON_PROMOTION;
            totalPlatformDiscount += DEFAULT_PLATFORM_DISCOUNT_PROMOTION;
            totalPlatformCoupon += DEFAULT_PLATFORM_COUPON_PROMOTION;

            totalInitialShippingFee += DEFAULT_INITIAL_SHIPPING_FEE;
            totalSellerShippingPromotion += DEFAULT_SELLER_SHIPPING_PROMOTION;
            totalPlatformShippingPromotion += DEFAULT_PLATFORM_SHIPPING_PROMOTION;
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
}