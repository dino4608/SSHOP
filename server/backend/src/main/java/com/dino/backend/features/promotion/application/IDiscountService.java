package com.dino.backend.features.promotion.application;

import java.util.List;
import java.util.Optional;

import com.dino.backend.features.productcatalog.domain.Sku;
import com.dino.backend.features.promotion.application.model.DiscountItemRes;
import com.dino.backend.features.promotion.domain.Discount;
import com.dino.backend.shared.api.model.CurrentUser;

public interface IDiscountService {
    // QUERY //

    Optional<Discount> canApply(List<Discount> discounts, CurrentUser currentUser);

    Optional<Discount> canApply(Long productId, CurrentUser currentUser);

    Optional<Discount> canApply(String productId, CurrentUser currentUser);

    DiscountItemRes canDiscount(Sku sku, CurrentUser currentUser);
}
