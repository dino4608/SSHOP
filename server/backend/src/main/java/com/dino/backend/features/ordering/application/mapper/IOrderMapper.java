package com.dino.backend.features.ordering.application.mapper;

import com.dino.backend.features.ordering.application.model.OrderItemRes;
import com.dino.backend.features.ordering.application.model.OrderRes;
import com.dino.backend.features.ordering.domain.Order;
import com.dino.backend.features.ordering.domain.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IOrderMapper {

    OrderItemRes toOrderItemRes(OrderItem orderItem);

    OrderRes toOrderRes(Order order);
}
