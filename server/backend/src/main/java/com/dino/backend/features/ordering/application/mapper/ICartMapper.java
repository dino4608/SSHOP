package com.dino.backend.features.ordering.application.mapper;

import com.dino.backend.features.ordering.application.model.CartGroupRes;
import com.dino.backend.features.ordering.application.model.CartItemRes;
import com.dino.backend.features.ordering.application.model.CartRes;
import com.dino.backend.features.ordering.domain.Cart;
import com.dino.backend.features.ordering.domain.CartItem;
import com.dino.backend.features.promotion.application.model.DiscountItemRes;
import com.dino.backend.features.shop.domain.Shop;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ICartMapper {

    @Mapping(source = "cartItem.sku.product", target = "product")
    @Mapping(source = "cartItem.sku", target = "sku")
    CartItemRes toCartItemRes(CartItem cartItem);

    @Mapping(source = "cartItem.sku.product", target = "product")
    @Mapping(source = "cartItem.sku", target = "sku")
    @Mapping(source = "photo", target = "photo")
    @Mapping(source = "discountItemRes", target = "discountItem")
    CartItemRes toCartItemRes(CartItem cartItem, String photo, DiscountItemRes discountItemRes);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "shop", target = "shop")
    @Mapping(source = "cartItemsRes", target = "cartItems")
    CartGroupRes toCartGroupRes(Long id, Shop shop, List<CartItemRes> cartItemsRes);

    @Mapping(source = "cartGroupsRes", target = "cartGroups")
    CartRes toCartRes(Cart cart, List<CartGroupRes> cartGroupsRes);
}