package com.dino.backend.features.promotion.application.service;

import com.dino.backend.features.productcatalog.domain.Sku;
import com.dino.backend.features.promotion.domain.Discount;
import com.dino.backend.shared.api.model.CurrentUser;

import java.util.List;
import java.util.Optional;

public interface IDiscountService {
    // QUERY //

    Optional<Discount> canDiscount(List<Discount> discounts, CurrentUser currentUser);

    Optional<Discount> canDiscount(Long productId, CurrentUser currentUser);

    Optional<Discount> canDiscount(Sku sku, CurrentUser currentUser);

    // COMMAND //
}
