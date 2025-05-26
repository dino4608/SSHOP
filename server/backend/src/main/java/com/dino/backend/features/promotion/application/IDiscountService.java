package com.dino.backend.features.promotion.application;

import java.util.List;
import java.util.Optional;

import com.dino.backend.features.promotion.domain.Discount;
import com.dino.backend.infrastructure.web.model.CurrentUser;

public interface IDiscountService {
    // QUERY //

    Optional<Discount> canApply(String productId, CurrentUser currentUser);

    Optional<Discount> canApply(List<Discount> discounts, CurrentUser currentUser);
}
