package com.dino.backend.features.ordering.application.impl;

import com.dino.backend.features.ordering.application.ICartService;
import com.dino.backend.features.ordering.application.ICheckoutServiceV2;
import com.dino.backend.features.ordering.application.mapper.ICartMapper;
import com.dino.backend.features.ordering.application.model.*;
import com.dino.backend.features.ordering.domain.Cart;
import com.dino.backend.features.ordering.domain.CartItem;
import com.dino.backend.features.ordering.domain.repository.ICartRepository;
import com.dino.backend.features.productcatalog.application.ISkuService; // Vẫn cần ISkuService nếu có logic khác
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
public class CheckoutServiceImplV2 implements ICheckoutServiceV2 {

    ICartService cartService;
    ICartRepository cartRepository;
    ISkuService skuService; // Giữ lại nếu có các logic khác cần SkuService
    IDiscountService discountService;
    ICartMapper cartMapper; // Giữ lại nếu có các mapping khác cần CartMapper

    private static final int DEFAULT_INITIAL_SHIPPING_FEE = 36000;
    private static final int DEFAULT_SHIPPING_DISCOUNT = -36000;

    // QUERY //

    /**
     * previewCheckout.
     * Tính toán và trả về thông tin xem trước đơn hàng dựa trên các cart item IDs được chọn.
     *
     * @param request      Chứa danh sách cartItemIds được chọn.
     * @param currentUser  Thông tin người dùng hiện tại.
     * @return CheckoutPreviewRes chứa thông tin tổng quan về giỏ hàng.
     */
    @Override
    public CheckoutPreviewResV2 previewCheckout(PreviewCheckoutReq request, CurrentUser currentUser) {
        // 1. Lấy giỏ hàng của người dùng với tất cả các mối quan hệ cần thiết
        Cart cart = cartRepository.findWithShopByBuyerId(currentUser.id())
                .orElseThrow(() -> new AppException(ErrorCode.CART__NOT_FOUND));

        // 2. Lọc ra các cart item được chọn từ giỏ hàng dựa trên cartItemIds trong request
        List<CartItem> selectedCartItems = cart.getCartItems().stream()
                .filter(item -> request.getCartItemIds().contains(item.getId()))
                .toList();

        // Nếu không có item nào được chọn, ném lỗi
        if (selectedCartItems.isEmpty()) {
            throw new AppException(ErrorCode.CHECKOUT__NO_SELECTED_ITEMS);
        }

        // 3. Nhóm các cart item đã chọn theo Shop. Mỗi nhóm là một đơn hàng giả định.
        Map<Shop, List<CartItem>> itemsGroupedByShop = selectedCartItems.stream()
                .collect(Collectors.groupingBy(item -> item.getSku().getProduct().getShop()));

        // 4. Xử lý từng nhóm (đơn hàng giả định) để tính toán chi tiết các tổng
        // Không cần tạo OrderPreviewRes chi tiết hay CartItemRes ở đây nữa,
        // chỉ tính toán các giá trị số học cần thiết cho summary.
        List<Map<String, Integer>> orderCalculations = itemsGroupedByShop.entrySet().stream()
                .map(entry -> {
                    List<CartItem> cartItemsInOrder = entry.getValue();

                    Integer orderItemsPrice = 0; // Tổng tiền hàng của đơn hàng này (tổng retail price)
                    Integer sellerProductDiscount = 0; // Tổng giảm giá sản phẩm của người bán cho đơn hàng này

                    for (CartItem cartItem : cartItemsInOrder) {
                        // Không gọi skuService.getPhoto() vì không cần photo trong summary
                        DiscountItemRes discountItemRes = this.discountService.canDiscount(cartItem.getSku(), currentUser);

                        // Tính toán giá bán lẻ cho item hiện tại (retailPrice * quantity)
                        Integer itemRetailPrice = cartItem.getSku().getRetailPrice() * cartItem.getQuantity();
                        Integer itemDealPrice = itemRetailPrice; // Mặc định là giá bán lẻ

                        // Nếu có giảm giá áp dụng, tính toán giá ưu đãi và cộng dồn vào tổng giảm giá sản phẩm
                        if (discountItemRes != null && discountItemRes.dealPrice() != null) {
                            itemDealPrice = discountItemRes.dealPrice() * cartItem.getQuantity();
                            sellerProductDiscount += (itemRetailPrice - itemDealPrice);
                        }
                        orderItemsPrice += itemRetailPrice; // Cộng dồn tổng retail price
                    }

                    Integer orderDiscountAmount = sellerProductDiscount;

                    // Các loại giảm giá khác (chưa thực thi, mặc định 0)
                    Integer sellerCouponDiscount = 0;
                    Integer platformDiscount = 0;

                    // Tính toán phí vận chuyển cho đơn hàng này
                    Integer initialShippingFee = DEFAULT_INITIAL_SHIPPING_FEE;
                    Integer shippingDiscount = DEFAULT_SHIPPING_DISCOUNT;
                    Integer shippingFee = initialShippingFee + shippingDiscount;

                    // Trả về một Map chứa các tổng của đơn hàng này
                    return Map.of(
                            "orderItemsPrice", orderItemsPrice,
                            "orderDiscountAmount", orderDiscountAmount,
                            "shippingFee", shippingFee
                    );
                })
                .toList(); // Không cần sắp xếp hay lọc groupId ở đây vì chỉ cần tổng hợp

        // 5. Tổng hợp kết quả từ tất cả các đơn hàng giả định để tạo CheckoutSummaryRes
        Integer totalItemsPrice = orderCalculations.stream().mapToInt(map -> map.get("orderItemsPrice")).sum();
        Integer totalDiscountAmount = orderCalculations.stream().mapToInt(map -> map.get("orderDiscountAmount")).sum();
        Integer totalShippingFee = orderCalculations.stream().mapToInt(map -> map.get("shippingFee")).sum();
        Integer totalPayableAmount = totalItemsPrice - totalDiscountAmount + totalShippingFee;

        // Tạo CheckoutSummaryRes (đối tượng tổng quan có thể tái sử dụng)
        CheckoutSummaryResV2 summary = new CheckoutSummaryResV2(
                totalItemsPrice,
                totalDiscountAmount,
                totalShippingFee,
                totalPayableAmount
        );

        // Trả về CheckoutPreviewRes cuối cùng, chỉ chứa cartId và summary
        return new CheckoutPreviewResV2(
                cart.getId(),
                summary
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
