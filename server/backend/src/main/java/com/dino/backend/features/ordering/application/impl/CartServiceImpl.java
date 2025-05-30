package com.dino.backend.features.ordering.application.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dino.backend.features.identity.application.IUserService;
import com.dino.backend.features.ordering.application.ICartService;
import com.dino.backend.features.ordering.application.mapper.ICartMapper;
import com.dino.backend.features.ordering.application.model.AddCartItemReq;
import com.dino.backend.features.ordering.application.model.CartItemRes;
import com.dino.backend.features.ordering.application.model.CartRes;
import com.dino.backend.features.ordering.application.model.RemoveCartItemReq;
import com.dino.backend.features.ordering.domain.Cart;
import com.dino.backend.features.ordering.domain.repository.ICartRepository;
import com.dino.backend.features.productcatalog.application.ISkuService;
import com.dino.backend.infrastructure.aop.AppException;
import com.dino.backend.infrastructure.aop.ErrorCode;
import com.dino.backend.infrastructure.web.model.CurrentUser;
import com.dino.backend.shared.utils.Deleted;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

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
     * getOrCreateCart (get and join all)
     */
    private Cart getOrCreateCart(CurrentUser currentUser) {
        return this.cartRepository.findJoinAllByBuyerId(currentUser.id())
                .orElseGet(() -> this.createCart(currentUser));
    }

    /**
     * getCart (get and join CartItems)
     */
    private Cart getCart(CurrentUser currentUser) {
        return this.cartRepository.findJoinAllByBuyerId(currentUser.id())
                .orElseThrow(() -> new AppException(ErrorCode.CART__FIND_FAIL));
    }

    /**
     * createCart
     */
    private Cart createCart(CurrentUser currentUser) {
        var buyer = this.userService.get(currentUser);
        var newCart = Cart.createCart(buyer);
        return this.saveCart(newCart);
    }

    /**
     * save
     */
    private Cart saveCart(Cart cart) {
        return this.cartRepository.save(cart);
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
        // 1. get Cart
        var cart = this.getOrCreateCart(currentUser);

        // 2. check CartItem is existing => increaseQuantity or addCartItem
        var upsertedCartItem = cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getSku().getId().equals(request.getSkuId()))
                .findFirst()
                .map(cartItem -> {
                    Cart.increaseQuantity(cartItem, request.getQuantity());
                    return cartItem;
                })
                .orElseGet(() -> {
                    var sku = this.skuService.getSku(request.getSkuId());
                    return Cart.addCartItem(cart, sku, request.getQuantity());
                });
        this.saveCart(cart);

        return this.cartMapper.toCartItemRes(upsertedCartItem);
    }

    /**
     * updateQuantity
     */
    @Override
    public CartItemRes updateQuantity(AddCartItemReq request, CurrentUser currentUser) {
        // 1. get Cart
        var cart = this.getCart(currentUser);

        // 2. check CartItem is existing => updateQuantity or addCartItem
        var upsertedCartItem = cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getSku().getId().equals(request.getSkuId()))
                .findFirst()
                .map(cartItem -> {
                    Cart.updateQuantity(cartItem, request.getQuantity());
                    return cartItem;
                })
                .orElseGet(() -> {
                    var sku = this.skuService.getSku(request.getSkuId());
                    return Cart.addCartItem(cart, sku, request.getQuantity());
                });
        this.saveCart(cart);

        return this.cartMapper.toCartItemRes(upsertedCartItem);
    }

    /**
     * removeCartItems
     */
    @Override
    public Deleted removeCartItems(RemoveCartItemReq request, CurrentUser currentUser) {
        // 1. get Cart
        var cart = this.getCart(currentUser);

        // 2. removeCartItem
        var deleted = Cart.removeCartItems(cart, request.getSkuIds());
        this.saveCart(cart);

        return deleted;
    }
}