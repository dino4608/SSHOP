package com.dino.backend.features.ordering.domain.model;

import com.dino.backend.shared.domain.exception.AppException;
import com.dino.backend.shared.domain.exception.ErrorCode;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShippingDetail {
    String carrier;
    Instant minEstimatedDelivery;
    Instant maxEstimatedDelivery;

    // SETTER //

    public void setMinEstimatedDelivery(Instant minEstimatedDelivery) {
        Instant now = Instant.now();

        if (minEstimatedDelivery == null || !minEstimatedDelivery.isAfter(now))
            throw new AppException(ErrorCode.SYSTEM__DEVELOPING_FEATURE);

        if (this.maxEstimatedDelivery != null && !this.maxEstimatedDelivery.isAfter(minEstimatedDelivery))
            throw new AppException(ErrorCode.SYSTEM__DEVELOPING_FEATURE);

        this.minEstimatedDelivery = minEstimatedDelivery;
    }

    public void setMaxEstimatedDelivery(Instant maxEstimatedDelivery) {
        if (maxEstimatedDelivery == null)
            throw new AppException(ErrorCode.SYSTEM__DEVELOPING_FEATURE);

        if (this.minEstimatedDelivery != null && !maxEstimatedDelivery.isAfter(this.minEstimatedDelivery))
            throw new AppException(ErrorCode.SYSTEM__DEVELOPING_FEATURE);

        this.maxEstimatedDelivery = maxEstimatedDelivery;
    }

    // FACTORY METHOD //

    /**
     * createShippingDetail.
     */
    public static ShippingDetail createShippingDetail(
            String carrier, Instant minEstimatedDelivery, Instant maxEstimatedDelivery
    ) {
        ShippingDetail shippingDetail = new ShippingDetail();
        shippingDetail.setCarrier(carrier);
        shippingDetail.setMinEstimatedDelivery(minEstimatedDelivery);
        shippingDetail.setMaxEstimatedDelivery(maxEstimatedDelivery);
        return shippingDetail;
    }
}
