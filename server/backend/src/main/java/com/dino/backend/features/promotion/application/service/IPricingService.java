package com.dino.backend.features.promotion.application.service;

import com.dino.backend.features.productcatalog.domain.Sku;
import com.dino.backend.features.promotion.application.model.SkuPrice;
import com.dino.backend.features.promotion.domain.Discount;
import com.dino.backend.shared.api.model.CurrentUser;

public interface IPricingService {

    SkuPrice calculateDiscount(Sku sku, Discount discount);

    SkuPrice calculatePrice(Sku sku, CurrentUser currentUser);
}
