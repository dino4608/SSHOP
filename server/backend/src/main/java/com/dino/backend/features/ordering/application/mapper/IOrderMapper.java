package com.dino.backend.features.ordering.application.mapper;

import com.dino.backend.features.ordering.application.model.DraftOrderRes;
import com.dino.backend.features.ordering.application.model.OrderItemRes;
import com.dino.backend.features.ordering.domain.Order;
import com.dino.backend.features.ordering.domain.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IOrderMapper {

    @Mapping(source = "orderItem.sku.product", target = "product")
    OrderItemRes toOrderItemRes(OrderItem orderItem);

    DraftOrderRes toDraftOrderRes(Order order);
}
