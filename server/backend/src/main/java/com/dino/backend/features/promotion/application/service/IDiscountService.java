package com.dino.backend.features.promotion.application.service;

import java.util.List;
import java.util.Optional;

import com.dino.backend.features.productcatalog.domain.Sku;
import com.dino.backend.features.promotion.application.model.DiscountItemRes;
import com.dino.backend.features.promotion.domain.Discount;
import com.dino.backend.shared.api.model.CurrentUser;

public interface IDiscountService {
    // QUERY //

    Optional<Discount> canDiscount(List<Discount> discounts, CurrentUser currentUser);

    Optional<Discount> canDiscount(Long productId, CurrentUser currentUser);

    Optional<Discount> canDiscount(String productId, CurrentUser currentUser);

    Optional<Discount> canDiscount(Sku sku, CurrentUser currentUser);

    DiscountItemRes canDiscountAndCalculate(Sku sku, CurrentUser currentUser);

    // COMMAND //
}
