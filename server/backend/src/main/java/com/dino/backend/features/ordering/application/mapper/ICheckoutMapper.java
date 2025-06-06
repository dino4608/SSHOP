package com.dino.backend.features.ordering.application.mapper;

import com.dino.backend.features.ordering.application.model.EstimateCheckoutRes;
import com.dino.backend.features.ordering.application.model.InitCheckoutRes;
import com.dino.backend.features.ordering.application.model.OrderRes;
import com.dino.backend.features.ordering.domain.model.CheckoutSnapshot;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ICheckoutMapper {

    EstimateCheckoutRes toEstimateCheckoutRes(
            Long cartId,
            CheckoutSnapshot checkoutSnapshot);

    InitCheckoutRes toInitCheckoutRes(
            Long checkoutId,
            CheckoutSnapshot totalCheckoutSnapshot,
            List<OrderRes> orders);
}