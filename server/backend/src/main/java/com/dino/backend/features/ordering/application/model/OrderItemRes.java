package com.dino.backend.features.ordering.application.model;

import com.dino.backend.features.productcatalog.application.model.ProductLean;
import com.dino.backend.features.productcatalog.application.model.SkuLean;

public record OrderItemRes(
        Long id,
        String photo,
        int quantity,
        int effectivePrice,
        ProductLean product,
        SkuLean sku) {
}
