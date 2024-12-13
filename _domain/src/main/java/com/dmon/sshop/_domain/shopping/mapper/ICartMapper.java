package com.dmon.sshop._domain.shopping.mapper;

import com.dmon.sshop._domain.shopping.model.entity.CartItem;
import com.dmon.sshop._domain.shopping.model.request.CartItemAddReq;
import com.dmon.sshop._domain.shopping.model.request.CartItemEditReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICartMapper {
    CartItem toEntity(CartItemAddReq cartItemReq);
    CartItem toEntity(CartItemEditReq cartItemEditReq);
    CartItemEditReq toReq(CartItem cartItem);
}
