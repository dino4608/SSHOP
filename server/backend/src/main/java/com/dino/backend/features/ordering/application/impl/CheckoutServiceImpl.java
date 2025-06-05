package com.dino.backend.features.ordering.application.impl;

import com.dino.backend.features.ordering.application.ICartService;
import com.dino.backend.features.ordering.application.ICheckoutService;
import com.dino.backend.features.ordering.application.mapper.ICartMapper;
import com.dino.backend.features.ordering.application.model.CartItemRes;
import com.dino.backend.features.ordering.application.model.CheckoutPreviewRes;
import com.dino.backend.features.ordering.application.model.PreviewCheckoutReq;
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

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CheckoutServiceImpl implements ICheckoutService {

    ICartService cartService;
    ICartRepository cartRepository;
    ISkuService skuService;
    IDiscountService discountService;
    ICartMapper cartMapper;

    private static final int DEFAULT_INITIAL_SHIPPING_FEE = 36000;
    private static final int DEFAULT_SHIPPING_DISCOUNT = -36000;

    // QUERY //

    /**
     * previewCheckout.
     * Tính toán và trả về thông tin xem trước đơn hàng dựa trên các cart item IDs được chọn.
     *
     * @param request     Chứa danh sách cartItemIds được chọn.
     * @param currentUser Thông tin người dùng hiện tại.
     * @return CheckoutPreviewRes chứa thông tin chi tiết về các đơn hàng giả định và tổng cộng.
     */
    @Override
    public CheckoutPreviewRes previewCheckout(PreviewCheckoutReq request, CurrentUser currentUser) {
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

        // 4. Xử lý từng nhóm (đơn hàng giả định) để tính toán chi tiết và tạo OrderPreviewRes
        List<CheckoutPreviewRes.OrderPreviewRes> orderPreviews = itemsGroupedByShop.entrySet().stream()
                .map(entry -> {
                    Shop shop = entry.getKey(); // Shop của đơn hàng giả định này
                    List<CartItem> cartItemsInOrder = entry.getValue(); // Các cart item thuộc đơn hàng này

                    // Chuyển đổi CartItem entities thành CartItemRes DTOs
                    // và tính toán các giá trị trung gian cần thiết cho tổng giảm giá
                    List<CartItemRes> orderCartItemRes = cartItemsInOrder.stream()
                            .map(cartItem -> {
                                String cartItemPhoto = this.skuService.getPhoto(cartItem.getSku());
                                DiscountItemRes discountItemRes = this.discountService.canDiscount(cartItem.getSku(), currentUser);

                                // Map CartItem entity sang CartItemRes DTO, truyền photo và discountItemRes
                                return this.cartMapper.toCartItemRes(cartItem, cartItemPhoto, discountItemRes);
                            })
                            // Sắp xếp các cart item trong đơn hàng theo ID giảm dần
                            .sorted(Comparator.comparing(CartItemRes::id).reversed())
                            .toList();

                    // Tính toán tổng tiền hàng (retail price) cho đơn hàng này
                    Integer orderItemsPrice = orderCartItemRes.stream()
                            .mapToInt(item -> item.sku().retailPrice() * item.quantity())
                            .sum();

                    // Tính toán tổng giảm giá sản phẩm của người bán cho đơn hàng này
                    // Đây là tổng của (giá bán lẻ - giá ưu đãi) cho mỗi item có giảm giá
                    Integer sellerProductDiscount = orderCartItemRes.stream()
                            .mapToInt(itemRes -> {
                                Integer itemRetailPrice = itemRes.sku().retailPrice() * itemRes.quantity();
                                Integer itemDealPrice = itemRetailPrice; // Mặc định là giá bán lẻ
                                if (itemRes.discountItem() != null && itemRes.discountItem().dealPrice() != null) {
                                    itemDealPrice = itemRes.discountItem().dealPrice() * itemRes.quantity();
                                }
                                return itemRetailPrice - itemDealPrice;
                            })
                            .sum();

                    // Tổng giảm giá cho đơn hàng này (hiện tại chỉ bao gồm giảm giá sản phẩm của người bán)
                    Integer orderDiscountAmount = sellerProductDiscount;

                    // Các loại giảm giá khác (chưa thực thi, mặc định 0)
                    Integer sellerCouponDiscount = 0;
                    Integer platformDiscount = 0;

                    // Tính toán phí vận chuyển cho đơn hàng này
                    Integer initialShippingFee = DEFAULT_INITIAL_SHIPPING_FEE;
                    Integer shippingDiscount = DEFAULT_SHIPPING_DISCOUNT;
                    Integer shippingFee = initialShippingFee + shippingDiscount; // Phí vận chuyển cuối cùng

                    // Trả về OrderPreviewRes cho đơn hàng giả định này
                    return new CheckoutPreviewRes.OrderPreviewRes(
                            // groupId: Lấy ID của CartItem mới nhất trong nhóm để làm ID nhóm
                            orderCartItemRes.stream().findFirst().map(CartItemRes::id).orElse(null),
                            new CheckoutPreviewRes.OrderPreviewRes.ShopRes(shop.getId(), shop.getName()),
                            orderCartItemRes,
                            orderItemsPrice,
                            orderDiscountAmount,
                            sellerProductDiscount,
                            sellerCouponDiscount,
                            platformDiscount,
                            shippingFee,
                            initialShippingFee,
                            shippingDiscount
                    );
                })
                // Lọc bỏ các nhóm không có ID hợp lệ
                .filter(orderPreviewRes -> Objects.nonNull(orderPreviewRes.groupId()))
                // Sắp xếp các đơn hàng giả định theo Group ID giảm dần
                .sorted(Comparator.comparing(CheckoutPreviewRes.OrderPreviewRes::groupId).reversed())
                .toList();

        // 5. Tổng hợp kết quả từ tất cả các đơn hàng giả định để tạo CheckoutPreviewRes tổng thể
        Integer totalItemsPrice = orderPreviews.stream().mapToInt(CheckoutPreviewRes.OrderPreviewRes::orderItemsPrice).sum();
        Integer totalDiscountAmount = orderPreviews.stream().mapToInt(CheckoutPreviewRes.OrderPreviewRes::orderDiscountAmount).sum();
        Integer totalShippingFee = orderPreviews.stream().mapToInt(CheckoutPreviewRes.OrderPreviewRes::shippingFee).sum();
        Integer totalPayableAmount = totalItemsPrice - totalDiscountAmount + totalShippingFee;

        // Trả về CheckoutPreviewRes cuối cùng
        return new CheckoutPreviewRes(
                cart.getId(),
                totalItemsPrice,
                totalDiscountAmount,
                totalShippingFee,
                totalPayableAmount,
                orderPreviews
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

