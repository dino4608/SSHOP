package com.dino.backend.features.ordering.application.impl;

import com.dino.backend.features.identity.application.IUserService;
import com.dino.backend.features.ordering.application.ICartService;
import com.dino.backend.features.ordering.application.mapper.ICartMapper;
import com.dino.backend.features.ordering.application.model.*;
import com.dino.backend.features.ordering.domain.Cart;
import com.dino.backend.features.ordering.domain.repository.ICartRepository;
import com.dino.backend.features.productcatalog.application.ISkuService;
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

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CartServiceImpl implements ICartService {

    IUserService userService;
    ISkuService skuService;
    ICartRepository cartRepository;
    ICartMapper cartMapper;

    // DOMAIN //

    /**
     * getCart. (get and join all)
     */
    private Cart getCart(CurrentUser currentUser) {
        return this.cartRepository.findJoinAllByBuyerId(currentUser.id())
                .orElseThrow(() -> new AppException(ErrorCode.CART__NOT_FOUND));
    }

    /**
     * getOrCreateCart. (get and join all)
     */
    private Cart getOrCreateCart(CurrentUser currentUser) {
        var cart = this.cartRepository.findJoinAllByBuyerId(currentUser.id());
        if (cart.isPresent())
            return cart.get();

        var cartIsDeleted = this.cartRepository.findIsDeletedByBuyerId(currentUser.id());
        if (cartIsDeleted.isPresent())
            throw new AppException(ErrorCode.CART__IS_DELETED);

        return this.createCart(currentUser);
    }

    /**
     * createCart
     */
    private Cart createCart(CurrentUser currentUser) {
        var buyer = this.userService.get(currentUser);
        var newCart = Cart.createCart(buyer);
        return this.cartRepository.save(newCart);
    }

    // QUERY //

    /**
     * get
     */
    @Override
    public CartRes get(CurrentUser currentUser) {
        var cart = this.getOrCreateCart(currentUser);
        return this.cartMapper.toCartRes(cart);
    }

    // COMMAND //

    /**
     * addCartItem
     */
    @Override
    @Transactional
    public CartItemRes addCartItem(AddCartItemReq request, CurrentUser currentUser) {
        // 1. get Cart and Sku
        var cart = this.getOrCreateCart(currentUser);
        var sku = this.skuService.getSku(request.getSkuId());

        // 2. addOrUpdateCartItem
        var upsertedCartItem = cart.addOrUpdateCartItem(sku, request.getQuantity());
        this.cartRepository.save(cart);

        return this.cartMapper.toCartItemRes(upsertedCartItem);
    }

    /**
     * updateQuantity
     */
    @Override
    public CartItemRes updateQuantity(UpdateQuantityReq request, CurrentUser currentUser) {
        // 1. get Cart
        var cart = this.getCart(currentUser);

        // 2. updateQuantity
        var updatedCartItem = cart.updateQuantity(request.getCartItemId(), request.getQuantity());
        this.cartRepository.save(cart);

        return this.cartMapper.toCartItemRes(updatedCartItem);
    }

    /**
     * removeCartItems
     */
    @Override
    public Deleted removeCartItems(RemoveCartItemReq request, CurrentUser currentUser) {
        // 1. get Cart
        var cart = this.getCart(currentUser);

        // 2. removeCartItem
        var removedCartItems = cart.removeCartItems(request.getSkuIds());
        this.cartRepository.save(cart);

        return Deleted.success(removedCartItems.size());
    }
}