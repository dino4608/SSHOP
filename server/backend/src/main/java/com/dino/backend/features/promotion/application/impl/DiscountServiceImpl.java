package com.dino.backend.features.promotion.application.impl;

import com.dino.backend.features.productcatalog.domain.Sku;
import com.dino.backend.features.promotion.application.IDiscountService;
import com.dino.backend.features.promotion.application.model.DiscountItemRes;
import com.dino.backend.features.promotion.domain.Discount;
import com.dino.backend.features.promotion.domain.repository.IDiscountRepository;
import com.dino.backend.shared.api.model.CurrentUser;
import com.dino.backend.shared.application.utils.Id;
import com.dino.backend.shared.domain.exception.AppException;
import com.dino.backend.shared.domain.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class DiscountServiceImpl implements IDiscountService {

    IDiscountRepository discountRepository;

    // HELPER //

    private Integer computeDiscountPercent(Integer retailPrice, Integer dealPrice) {
        if (retailPrice == null || retailPrice == 0 || dealPrice == null)
            return 0;

        double discountRatio = 1 - (dealPrice.doubleValue() / retailPrice.doubleValue());
        int discountPercent = (int) Math.round(discountRatio * 100);

        // đảm bảo kết quả trong khoảng 0..100
        if (discountPercent < 0) discountPercent = 0;
        if (discountPercent > 100) discountPercent = 100;

        return discountPercent;
    }

    private Integer computeDealPrice(Integer retailPrice, Integer discountPercent) {
        if (retailPrice == null || retailPrice == 0 || discountPercent == null)
            return retailPrice;

        double price = retailPrice * (100 - discountPercent) / 100.0;
        int dealPrice = (int) Math.round(price);

        // đảm bảo không âm
        if (dealPrice < 0) dealPrice = 0;

        return dealPrice;
    }

    // QUERY //

    // canApply by discounts //
    @Override
    public Optional<Discount> canApply(List<Discount> discounts, @Nullable CurrentUser currentUser) {
        var applicableDiscounts = discounts.stream()
                .filter(dp -> dp.canApply(currentUser))
                .toList();

        if (applicableDiscounts.isEmpty())
            return Optional.empty();

        if (applicableDiscounts.size() == 1)
            return Optional.of(applicableDiscounts.getFirst());

        return applicableDiscounts.stream()
                .min(Comparator.comparingInt(dp -> dp.getDiscountProgram().getPriority()));
    }

    // canApply to product //
    @Override
    public Optional<Discount> canApply(Long productId, @Nullable CurrentUser currentUser) {
        var discounts = this.discountRepository.findByProductId(productId);

        return this.canApply(discounts, currentUser);
    }

    // canApply to product //
    @Override
    public Optional<Discount> canApply(String productId, CurrentUser currentUser) {
        Id id = Id.from(productId).orElseThrow(() -> new AppException(ErrorCode.PRODUCT__NOT_FOUND));
        return this.canApply(id.value(), currentUser);
    }

    // canApply to Sku //
    private Optional<Discount> findDiscount(Sku sku, CurrentUser currentUser) {
        return this.canApply(sku.getProduct().getId(), currentUser);
    }

    // applyPricing Sku //
    private DiscountItemRes computeDiscount(Sku sku, Discount discount) {
        var dealPrice = discount.getDealPrice() != null
                // Discount ko có dealPrice => có discountPercent
                ? discount.getDealPrice()
                : discount.getDiscountPercent() != null
                ? this.computeDealPrice(sku.getRetailPrice(), discount.getDiscountPercent())
                : null;
        var discountPercent = discount.getDiscountPercent() != null
                // Discount ko có discountPercent => có dealPrice
                ? discount.getDiscountPercent()
                : discount.getDealPrice() != null
                ? this.computeDiscountPercent(sku.getRetailPrice(), discount.getDealPrice())
                : null;
        return DiscountItemRes.from(dealPrice, discountPercent);
    }


    @Override
    public DiscountItemRes canDiscount(Sku sku, CurrentUser currentUser) {
        return this.findDiscount(sku, currentUser)
                .map(discount -> this.computeDiscount(sku, discount))
                .orElse(DiscountItemRes.from());
    }

}
