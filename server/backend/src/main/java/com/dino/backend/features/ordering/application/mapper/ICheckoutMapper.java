package com.dino.backend.features.ordering.application.mapper;

import com.dino.backend.features.ordering.application.model.CheckoutSnapshotRes;
import com.dino.backend.features.ordering.application.model.EstimateCheckoutRes;
import com.dino.backend.features.ordering.application.model.InitCheckoutRes;
import com.dino.backend.features.ordering.application.model.OrderRes;
import com.dino.backend.features.ordering.domain.Order;
import com.dino.backend.features.ordering.domain.model.CheckoutSnapshot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

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

    CheckoutSnapshot.Summary toSummary(
            int effectivePrice,
            int savings,
            int shippingFee,
            int total);

    CheckoutSnapshot.DiscountVoucher toDiscountVoucher(
            int totalVoucher,
            int sellerVoucher,
            int platformVoucher);

    CheckoutSnapshot.ShippingFee toShippingFee(
            int finalFee,
            int initialFee,
            int sellerShippingVoucher,
            int platformShippingVoucher);

    @Mapping(source = "summary", target = "summary")
    @Mapping(source = "discountVoucher", target = "discountVoucher")
    @Mapping(source = "shippingFee", target = "shippingFee")
    CheckoutSnapshot toCheckoutSnapshot(
            CheckoutSnapshot.Summary summary,
            CheckoutSnapshot.DiscountVoucher discountVoucher,
            CheckoutSnapshot.ShippingFee shippingFee);

    InitCheckoutRes toInitCheckoutRes(
            Long checkoutId,
            CheckoutSnapshot totalCheckoutSnapshot,
            List<OrderRes> orders);
}