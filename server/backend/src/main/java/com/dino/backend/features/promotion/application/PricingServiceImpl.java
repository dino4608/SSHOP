package com.dino.backend.features.promotion.application;

import com.dino.backend.features.productcatalog.domain.Sku;
import com.dino.backend.features.promotion.application.model.SkuPrice;
import com.dino.backend.features.promotion.application.service.IDiscountService;
import com.dino.backend.features.promotion.application.service.IPricingService;
import com.dino.backend.features.promotion.domain.Discount;
import com.dino.backend.shared.api.model.CurrentUser;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PricingServiceImpl implements IPricingService {

    IDiscountService discountService;

    // HELPER //

    private Integer calculateDiscountPercent(Integer retailPrice, Integer dealPrice) {
        if (retailPrice == null || retailPrice == 0 || dealPrice == null)
            return 0;

        double discountRatio = 1 - (dealPrice.doubleValue() / retailPrice.doubleValue());
        int discountPercent = (int) Math.round(discountRatio * 100);

        if (discountPercent < 0) discountPercent = 0;
        if (discountPercent > 100) discountPercent = 100;  // đảm bảo từ 0..100

        return discountPercent;
    }

    private Integer calculateDealPrice(Integer retailPrice, Integer discountPercent) {
        if (retailPrice == null || retailPrice == 0 || discountPercent == null)
            return retailPrice;

        double price = retailPrice * (100 - discountPercent) / 100.0;
        int dealPrice = (int) Math.round(price);

        if (dealPrice < 0) dealPrice = 0; // đảm bảo không âm

        return dealPrice;
    }

    private SkuPrice calculateRetail(Sku sku) {
        return new SkuPrice(sku.getRetailPrice(), 0, 0);
    }

    // QUERY //

    // calculatePrice Sku //
    @Override
    public SkuPrice calculateDiscount(Sku sku, Discount discount) {
        Integer retailPrice = sku.getRetailPrice();
        // dealPrice = dealPrice | calculateDealPrice by discountPercent | null
        Integer dealPrice = discount.getDealPrice() != null
                ? discount.getDealPrice()
                : discount.getDiscountPercent() != null
                ? this.calculateDealPrice(sku.getRetailPrice(), discount.getDiscountPercent())
                : null;
        // discountPercent = discountPercent | calculateDiscountPercent by dealPrice | null
        Integer discountPercent = discount.getDiscountPercent() != null
                // Discount ko có discountPercent => có dealPrice
                ? discount.getDiscountPercent()
                : discount.getDealPrice() != null
                ? this.calculateDiscountPercent(sku.getRetailPrice(), discount.getDealPrice())
                : null;

        if (dealPrice == null || discountPercent == null) {
            dealPrice = sku.getRetailPrice();
            retailPrice = 0;
            discountPercent = 0;
        }

        return new SkuPrice(dealPrice, retailPrice, discountPercent);
    }

    @Override
    public SkuPrice calculatePrice(Sku sku, CurrentUser currentUser) {
        return this.discountService.canDiscount(sku, currentUser)
                .map(discount -> this.calculateDiscount(sku, discount))
                .orElseGet(() -> calculateRetail(sku));
    }
}
