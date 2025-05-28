package com.dino.backend.features.ordering.application.impl;

import com.dino.backend.features.identity.application.IUserService;
import com.dino.backend.features.ordering.application.ICartService;
import com.dino.backend.features.ordering.application.mapper.ICartMapper;
import com.dino.backend.features.ordering.application.model.CartItemAddReq;
import com.dino.backend.features.ordering.application.model.CartItemAddRes;
import com.dino.backend.features.ordering.application.model.CartItemRemoveReq;
import com.dino.backend.features.ordering.application.model.CartRes;
import com.dino.backend.features.ordering.domain.Cart;
import com.dino.backend.features.ordering.domain.repository.ICartRepository;
import com.dino.backend.features.productcatalog.application.ISkuService;
import com.dino.backend.infrastructure.aop.AppException;
import com.dino.backend.infrastructure.aop.ErrorCode;
import com.dino.backend.infrastructure.web.model.CurrentUser;
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
     * getOrCreateCart
     * @des get and join all
     */
    private Cart getOrCreateCart(CurrentUser currentUser) {
        return this.cartRepository.findJoinAllById(currentUser.id())
                .orElseGet(() -> this.createCart(currentUser));
    }

    /**
     * getCart
     * @des get and join CartItems
     */
    private Cart getCart(CurrentUser currentUser) {
        return this.cartRepository.findJoinItemsById(currentUser.id())
                .orElseThrow(() -> new AppException(ErrorCode.CART__FIND_FAIL));
    }

    /**
     * createCart
     */
    private Cart createCart(CurrentUser currentUser) {
        var buyer = this.userService.get(currentUser);
        var newCart = Cart.createCart(buyer);
        return this.save(newCart);
    }

    /**
     * save
     */
    private Cart save(Cart cart) {
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
    public CartItemAddRes addCartItem(CartItemAddReq request, CurrentUser currentUser) {
        // 1. get Cart, Sku
        var cart = this.getOrCreateCart(currentUser);
        var sku = this.skuService.getSku(request.getSkuId());

        // 3. check CartItem is existing => increaseQuantity or addCartItem
        var existingCartItem = cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getSku().getId().equals(sku.getId()))
                .findFirst();
        existingCartItem.ifPresentOrElse(
                cartItem -> Cart.increaseQuantity(cartItem, request.getQuantity()),
                () -> Cart.addCartItem(cart, sku, request.getQuantity()));
        var updatedCart = this.save(cart);

        // 4. get CartItem again to map to response
        var savedCartItem = updatedCart.getCartItems().stream()
                .filter(item -> item.getSku().getId().equals(sku.getId()))
                .findFirst()
                .orElseThrow(() -> new AppException(ErrorCode.CART__ADD_ITEM_FAIL));
        return this.cartMapper.toCartItemAddedRes(savedCartItem);
    }

    /**
     * removeCartItems
     */
    @Override
    @Transactional
    public void removeCartItems(CartItemRemoveReq request, CurrentUser currentUser) {
        // 1. get Cart
        var cart = this.getCart(currentUser);

        // 2. filter CartItem to remove
        var cartItemsToRemove = cart.getCartItems().stream()
                .filter(cartItem -> request.getCartItemIds().contains(cartItem.getId()))
                .toList();

        // 3. remove CartItem from Cart
        Cart.removeCartItem(cart, cartItemsToRemove);
        this.save(cart);
    }
}