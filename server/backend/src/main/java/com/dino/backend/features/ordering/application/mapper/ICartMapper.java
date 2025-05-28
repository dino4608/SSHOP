package com.dino.backend.features.ordering.application.mapper;

import java.util.List;
import java.util.Optional;

import com.dino.backend.features.ordering.application.model.CartItemAddRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import com.dino.backend.features.ordering.application.model.CartItemRes;
import com.dino.backend.features.ordering.application.model.CartRes;
import com.dino.backend.features.ordering.domain.Cart;
import com.dino.backend.features.ordering.domain.CartItem;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ICartMapper {

    @Mapping(source = "buyer.id", target = "buyerId")
    //@Mapping(target = "totalCount", expression = "java(calculateTotalCount(cart.getCartItems()))")
    //@Mapping(source = "cartItems", target = "cartItems") // Map list of CartItem to CartItemRes
    CartRes toCartRes(Cart cart);

    @Mapping(source = "sku.id", target = "skuId")
    @Mapping(source = "sku.code", target = "skuCode")
    @Mapping(source = "sku.tierOptionValue", target = "skuTierOptionValue")
    @Mapping(source = "sku.retailPrice", target = "retailPrice")
    @Mapping(source = "sku.product.name", target = "productName") // Lấy tên sản phẩm từ SKU
    @Mapping(source = "sku.product.thumb", target = "productThumb") // Lấy ảnh sản phẩm từ SKU
    CartItemRes toCartItemRes(CartItem cartItem);

    @Mapping(source = "sku.id", target = "skuId")
    @Mapping(source = "sku.code", target = "skuCode")
    @Mapping(source = "sku.tierOptionValue", target = "skuTierOptionValue")
    @Mapping(source = "sku.retailPrice", target = "retailPrice")
    @Mapping(source = "sku.product.name", target = "productName")
    @Mapping(source = "sku.product.thumb", target = "productThumb")
    CartItemAddRes toCartItemAddedRes(CartItem cartItem);

    // Helper method to calculate totalCount for CartRes
    @Named("calculateTotalCount")
    default int calculateTotalCount(List<CartItem> items) {
        return Optional.ofNullable(items)
                .orElse(List.of()) // Tránh NullPointerException nếu items là null
                .stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }
}