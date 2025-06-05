package com.dino.backend.features.ordering.application.mapper;

import com.dino.backend.features.ordering.application.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ICheckoutMapper {

    CheckoutSnapshotRes.SummaryRes toSummaryRes(
            Integer itemsPrice,
            Integer promotionAmount,
            Integer shippingFee,
            Integer payableAmount);

    CheckoutSnapshotRes.PricePromotionRes toPricePromotionRes(
            Integer totalAmount,
            Integer sellerDiscount,
            Integer sellerCoupon,
            Integer platformDiscount,
            Integer platformCoupon);

    CheckoutSnapshotRes.ShippingFeeRes toShippingFeeRes(
            Integer finalFee,
            Integer initialFee,
            Integer sellerShippingPromotion,
            Integer platformShippingPromotion);

    @Mapping(source = "summary", target = "summary")
    @Mapping(source = "pricePromotion", target = "pricePromotion")
    @Mapping(source = "shippingFee", target = "shippingFee")
    CheckoutSnapshotRes toCheckoutSnapshotRes(
            CheckoutSnapshotRes.SummaryRes summary,
            CheckoutSnapshotRes.PricePromotionRes pricePromotion,
            CheckoutSnapshotRes.ShippingFeeRes shippingFee);

    EstimateCheckoutRes toEstimateCheckoutRes(
            Long cartId,
            CheckoutSnapshotRes checkoutSnapshot);
}