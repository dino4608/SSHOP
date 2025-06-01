package com.dino.backend.features.productcatalog.domain.model;

import java.time.Instant;
import java.util.List;

import com.dino.backend.features.promotion.domain.Discount;

public interface ProductProjection {
    Long getId();

    String getStatus();

    Instant getUpdatedAt();

    String getName();

    String getThumb();

    Integer getRetailPrice();

    ProductMeta getMeta();

    List<Discount> getDiscounts();
}
