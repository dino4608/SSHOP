package com.dino.backend.features.ordering.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.dino.backend.features.ordering.application.model.CartItemRes;
import com.dino.backend.features.ordering.application.model.CartRes;
import com.dino.backend.features.ordering.domain.Cart;
import com.dino.backend.features.ordering.domain.CartItem;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ICartMapper {

    @Mapping(source = "buyer.id", target = "buyerId")
    CartRes toCartRes(Cart cart);

    @Mapping(source = "sku.id", target = "skuId")
    @Mapping(source = "sku.code", target = "skuCode")
    @Mapping(source = "sku.tierOptionValue", target = "skuTierOptionValue")
    @Mapping(source = "sku.retailPrice", target = "retailPrice")
    @Mapping(source = "sku.product.name", target = "productName")
    @Mapping(source = "sku.product.thumb", target = "productThumb")
    CartItemRes toCartItemRes(CartItem cartItem);
}