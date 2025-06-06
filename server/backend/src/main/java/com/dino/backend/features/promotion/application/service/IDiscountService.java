package com.dino.backend.features.promotion.application.service;

import com.dino.backend.features.productcatalog.domain.Product;
import com.dino.backend.features.productcatalog.domain.Sku;
import com.dino.backend.features.promotion.domain.Discount;
import com.dino.backend.shared.api.model.CurrentUser;

import java.util.Optional;

public interface IDiscountService {
    // QUERY //

    Optional<Discount> canDiscount(Product product);

    Optional<Discount> canDiscount(Product product, CurrentUser currentUser);

    Optional<Discount> canDiscount(Sku sku, CurrentUser currentUser);
}
