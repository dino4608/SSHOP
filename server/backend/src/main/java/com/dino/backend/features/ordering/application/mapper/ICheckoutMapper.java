package com.dino.backend.features.ordering.application.mapper;

import com.dino.backend.features.ordering.application.model.CheckoutDiscountInfoResV3;
import com.dino.backend.features.ordering.application.model.CheckoutShippingFeeResV3;
import com.dino.backend.features.ordering.application.model.CheckoutSummaryResV3;
import com.dino.backend.features.ordering.application.model.PreviewCheckoutResV3;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ICheckoutMapper {

    // Mapper cho CheckoutSummaryResV3
    CheckoutSummaryResV3 toCheckoutSummaryResV3(Integer totalItemsPrice, Integer totalDiscountAmount, Integer totalShippingFee, Integer totalPayableAmount);

    // Mapper cho CheckoutDiscountInfoResV3
    CheckoutDiscountInfoResV3 toCheckoutDiscountInfoResV3(Integer productDiscountOfSeller, Integer couponDiscountOfSeller, Integer discountPlatform);

    // Mapper cho CheckoutShippingFeeResV3
    CheckoutShippingFeeResV3 toCheckoutShippingFeeResV3(Integer finalShippingFee, Integer initialShippingFee, Integer shippingFeeDiscount);

    // Mapper chính cho PreviewCheckoutResV3
    @Mapping(source = "cartId", target = "cartId")
    @Mapping(source = "summary", target = "summary")
    @Mapping(source = "discountInfo", target = "discountInfo")
    @Mapping(source = "shippingFee", target = "shippingFee")
    PreviewCheckoutResV3 toPreviewCheckoutResV3(Long cartId, CheckoutSummaryResV3 summary, CheckoutDiscountInfoResV3 discountInfo, CheckoutShippingFeeResV3 shippingFee);
}