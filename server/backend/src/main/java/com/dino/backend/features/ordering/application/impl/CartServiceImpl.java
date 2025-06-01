package com.dino.backend.features.ordering.application.impl;

import com.dino.backend.features.identity.application.IUserService;
import com.dino.backend.features.ordering.application.ICartService;
import com.dino.backend.features.ordering.application.mapper.ICartMapper;
import com.dino.backend.features.ordering.application.model.*;
import com.dino.backend.features.ordering.domain.Cart;
import com.dino.backend.features.ordering.domain.CartItem;
import com.dino.backend.features.ordering.domain.repository.ICartRepository;
import com.dino.backend.features.productcatalog.application.ISkuService;
import com.dino.backend.features.promotion.application.IDiscountService;
import com.dino.backend.features.promotion.application.model.DiscountItemRes;
import com.dino.backend.features.shop.domain.Shop;
import com.dino.backend.shared.api.model.CurrentUser;
import com.dino.backend.shared.application.utils.Deleted;
import com.dino.backend.shared.domain.exception.AppException;
import com.dino.backend.shared.domain.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CartServiceImpl implements ICartService {

    IUserService userService;
    ISkuService skuService;
    IDiscountService discountService;
    ICartRepository cartRepository;
    ICartMapper cartMapper;

    // DOMAIN //

    /**
     * findCart. (find cart with sku)
     */
    private Optional<Cart> findCart(CurrentUser currentUser) {
        return this.cartRepository.findWithSkuByBuyerId(currentUser.id());
    }

    /**
     * getCart. (get cart with sku)
     */
    private Cart getCart(CurrentUser currentUser) {
        return this.cartRepository.findWithSkuByBuyerId(currentUser.id())
                .orElseThrow(() -> new AppException(ErrorCode.CART__NOT_FOUND));
    }

    /**
     * createCart.
     */
    private Cart createCart(CurrentUser currentUser) {
        // check is cart is deleted => to avoid one-to-one cart and buyer
        this.cartRepository.findIsDeletedByBuyerId(currentUser.id())
                .ifPresent(ignore -> {
                    throw new AppException(ErrorCode.CART__IS_DELETED);
                });
        // create
        var buyer = this.userService.get(currentUser);
        var newCart = Cart.createCart(buyer);
        return this.cartRepository.save(newCart);
    }

    // QUERY //

    /**
     * get. (get or create cart, map to response dto)
     */
    @Override
    public CartRes get(CurrentUser currentUser) {
        Cart cart = cartRepository.findWithShopByBuyerId(currentUser.id())
                .orElseGet(() -> this.createCart(currentUser));

        // 1. group CartItems by Shop
        Map<Shop, List<CartItem>> itemsGroupedByShop = cart.getCartItems().stream()
                .collect(Collectors.groupingBy(item -> item.getSku().getProduct().getShop()));

        // 2. build and sort CartGroups
        List<CartGroupRes> cartGroups = itemsGroupedByShop.entrySet().stream()
                .map(entry -> {
                    Shop shop = entry.getKey();
                    List<CartItem> cartItemsInGroup = entry.getValue();

                    // build CartItemsRes
                    List<CartItemRes> cartItemsRes = cartItemsInGroup.stream()
                            .map(cartItem -> {
                                // get photo of CartItem
                                var photo = this.skuService.getPhoto(cartItem.getSku());
                                // apply Discount to Sku
                                var skuPricing = this.discountService.canDiscount(
                                        cartItem.getSku(), currentUser);
                                var discountItemRes = DiscountItemRes.from();
                                // display CartItemRes
                                return this.cartMapper.toCartItemRes(cartItem, photo, discountItemRes);
                            })
                            .sorted(Comparator.comparing(CartItemRes::id).reversed())
                            .toList();
                    // get id of CartGroups
                    Long latestCartItemId = cartItemsRes.stream()
                            .findFirst()
                            .map(CartItemRes::id)
                            .orElse(null);

                    return this.cartMapper.toCartGroupRes(latestCartItemId, shop, cartItemsRes);
                })
                .filter(cartGroupRes -> Objects.nonNull(cartGroupRes.id()))
                .sorted(Comparator.comparing(CartGroupRes::id).reversed())
                .toList();

        return this.cartMapper.toCartRes(cart, cartGroups);
    }

    // COMMAND //

    /**
     * addCartItem
     */
    @Override
    @Transactional
    public CartItemRes addCartItem(AddCartItemReq request, CurrentUser currentUser) {
        var cart = this.findCart(currentUser).orElseGet(() -> createCart(currentUser));
        var sku = this.skuService.getSku(request.getSkuId());

        // 1. addOrUpdateCartItem
        var upsertedCartItem = cart.addOrUpdateCartItem(sku, request.getQuantity());
        this.cartRepository.save(cart);

        return this.cartMapper.toCartItemRes(upsertedCartItem);
    }

    /**
     * updateQuantity
     */
    @Override
    public CartItemRes updateQuantity(UpdateQuantityReq request, CurrentUser currentUser) {
        var cart = this.getCart(currentUser);

        // 1. updateQuantity
        var updatedCartItem = cart.updateQuantity(request.getCartItemId(), request.getQuantity());
        this.cartRepository.save(cart);

        return this.cartMapper.toCartItemRes(updatedCartItem);
    }

    /**
     * removeCartItems
     */
    @Override
    public Deleted removeCartItems(RemoveCartItemReq request, CurrentUser currentUser) {
        var cart = this.getCart(currentUser);

        // 1.. removeCartItem
        var removedCartItems = cart.removeCartItems(request.getCartItemIds());
        this.cartRepository.save(cart);

        return Deleted.success(removedCartItems.size());
    }
}