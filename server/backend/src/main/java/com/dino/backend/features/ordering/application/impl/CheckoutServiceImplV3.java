// CheckoutServiceImpl.java
package com.dino.backend.features.ordering.application.impl;

import com.dino.backend.features.ordering.application.ICartService;
import com.dino.backend.features.ordering.application.ICheckoutServiceV3;
import com.dino.backend.features.ordering.application.mapper.ICartMapper;
import com.dino.backend.features.ordering.application.mapper.ICheckoutMapper; // Import ICheckoutMapper mới
import com.dino.backend.features.ordering.application.model.*;
import com.dino.backend.features.ordering.domain.Cart;
import com.dino.backend.features.ordering.domain.CartItem;
import com.dino.backend.features.ordering.domain.repository.ICartRepository;
import com.dino.backend.features.productcatalog.application.ISkuService;
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
public class CheckoutServiceImplV3 implements ICheckoutServiceV3 {

    ICartService cartService; // Giữ lại nếu có các logic khác cần CartService
    ICartRepository cartRepository;
    ISkuService skuService;         // Giữ lại nếu có các logic khác cần SkuService
    IDiscountService discountService;
    ICartMapper cartMapper;         // Giữ lại nếu có các mapping khác liên quan đến Cart
    ICheckoutMapper checkoutMapper; // Đã được inject

    // Các hằng số mặc định cho phí vận chuyển và giảm giá
    private static final int DEFAULT_INITIAL_SHIPPING_FEE_PER_ORDER = 36000;
    private static final int DEFAULT_SHIPPING_DISCOUNT_PER_ORDER = -36000;
    private static final int DEFAULT_SELLER_COUPON_DISCOUNT = 0;
    private static final int DEFAULT_PLATFORM_DISCOUNT = 0;

    // QUERY //

    /**
     * previewCheckout.
     * Tính toán và trả về thông tin xem trước đơn hàng dựa trên các cart item IDs được chọn.
     *
     * @param request      Chứa danh sách cartItemIds được chọn.
     * @param currentUser  Thông tin người dùng hiện tại.
     * @return PreviewCheckoutResV3 chứa thông tin tổng quan về giỏ hàng.
     */
    @Override
    public PreviewCheckoutResV3 previewCheckout(PreviewCheckoutReq request, CurrentUser currentUser) {
        // 1. Lấy giỏ hàng của người dùng với tất cả các mối quan hệ cần thiết
        Cart cart = cartRepository.findWithShopByBuyerId(currentUser.id())
                .orElseThrow(() -> new AppException(ErrorCode.CART__NOT_FOUND));

        // 2. Lọc ra các cart item được chọn từ giỏ hàng dựa trên cartItemIds trong request
        List<CartItem> selectedCartItems = cart.getCartItems().stream()
                .filter(item -> request.getCartItemIds().contains(item.getId()))
                .toList();

        if (selectedCartItems.isEmpty()) {
            throw new AppException(ErrorCode.CHECKOUT__NO_SELECTED_ITEMS);
        }

        // 3. Nhóm các cart item đã chọn theo Shop. Mỗi nhóm là một đơn hàng giả định.
        Map<Shop, List<CartItem>> itemsGroupedByShop = selectedCartItems.stream()
                .collect(Collectors.groupingBy(item -> item.getSku().getProduct().getShop()));

        // Khởi tạo các tổng số cuối cùng cho toàn bộ preview checkout
        Integer totalItemsPrice = 0;
        Integer totalProductDiscountOfSeller = 0;
        Integer totalCouponDiscountOfSeller = 0;
        Integer totalPlatformDiscount = 0;
        Integer totalInitialShippingFee = 0;
        Integer totalShippingFeeDiscount = 0;

        // 4. Duyệt qua từng "đơn hàng giả định" để tính toán các tổng đóng góp
        // Logic này là cần thiết vì các phí vận chuyển và giảm giá coupon/platform được tính "trên mỗi đơn hàng giả định"
        for (Map.Entry<Shop, List<CartItem>> entry : itemsGroupedByShop.entrySet()) {
            List<CartItem> cartItemsInOrder = entry.getValue();

            Integer currentOrderProductDiscount = 0;
            Integer currentOrderItemsPrice = 0; // Retail price cho đơn hàng giả định hiện tại

            for (CartItem cartItem : cartItemsInOrder) {
                // Lấy thông tin giảm giá cho từng SKU
                DiscountItemRes discountItemRes = this.discountService.canDiscount(cartItem.getSku(), currentUser);

                Integer itemRetailPrice = cartItem.getSku().getRetailPrice() * cartItem.getQuantity();
                Integer itemDealPrice = itemRetailPrice;

                if (discountItemRes != null && discountItemRes.dealPrice() != null) {
                    itemDealPrice = discountItemRes.dealPrice() * cartItem.getQuantity();
                }

                currentOrderProductDiscount += (itemRetailPrice - itemDealPrice);
                currentOrderItemsPrice += itemRetailPrice;
            }

            // Cộng dồn các tổng từ đơn hàng giả định hiện tại vào tổng chung
            totalItemsPrice += currentOrderItemsPrice;
            totalProductDiscountOfSeller += currentOrderProductDiscount;

            // Các loại giảm giá khác (tính trên mỗi đơn hàng giả định và cộng dồn)
            totalCouponDiscountOfSeller += DEFAULT_SELLER_COUPON_DISCOUNT;
            totalPlatformDiscount += DEFAULT_PLATFORM_DISCOUNT;

            // Phí vận chuyển (tính trên mỗi đơn hàng giả định và cộng dồn)
            totalInitialShippingFee += DEFAULT_INITIAL_SHIPPING_FEE_PER_ORDER;
            totalShippingFeeDiscount += DEFAULT_SHIPPING_DISCOUNT_PER_ORDER;
        }

        // 5. Tính toán các tổng cuối cùng từ các giá trị đã cộng dồn
        Integer totalFinalShippingFee = totalInitialShippingFee + totalShippingFeeDiscount;
        Integer totalDiscountAmount = totalProductDiscountOfSeller + totalCouponDiscountOfSeller + totalPlatformDiscount;
        Integer totalPayableAmount = totalItemsPrice - totalDiscountAmount + totalFinalShippingFee;

        // 6. Tạo các đối tượng response SỬ DỤNG ICheckoutMapper
        CheckoutSummaryResV3 summary = checkoutMapper.toCheckoutSummaryResV3(
                totalItemsPrice,
                totalDiscountAmount,
                totalFinalShippingFee,
                totalPayableAmount
        );

        CheckoutDiscountInfoResV3 discountInfo = checkoutMapper.toCheckoutDiscountInfoResV3(
                totalProductDiscountOfSeller,
                totalCouponDiscountOfSeller,
                totalPlatformDiscount
        );

        CheckoutShippingFeeResV3 shippingFee = checkoutMapper.toCheckoutShippingFeeResV3(
                totalFinalShippingFee,
                totalInitialShippingFee,
                totalShippingFeeDiscount
        );

        // 7. Trả về response cuối cùng SỬ DỤNG ICheckoutMapper
        return checkoutMapper.toPreviewCheckoutResV3(
                cart.getId(),
                summary,
                discountInfo,
                shippingFee
        );
    }

    @Override
    public Object checkoutOrder(CurrentUser currentUser) {
        return null;
    }

    @Override
    public Object confirmCheckout(CurrentUser currentUser) {
        return null;
    }
}