package com.dino.backend.features.promotion.application;

import com.dino.backend.features.productcatalog.domain.Sku;
import com.dino.backend.features.promotion.application.service.IDiscountService;
import com.dino.backend.features.promotion.domain.Discount;
import com.dino.backend.features.promotion.domain.repository.IDiscountRepository;
import com.dino.backend.shared.api.model.CurrentUser;
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

    // QUERY //

    // canApply by discounts //
    @Override
    public Optional<Discount> canDiscount(List<Discount> discounts, @Nullable CurrentUser currentUser) {
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
    public Optional<Discount> canDiscount(Long productId, @Nullable CurrentUser currentUser) {
        var discounts = this.discountRepository.findByProductId(productId);

        return this.canDiscount(discounts, currentUser);
    }

    // canApply to Sku //
    @Override
    public Optional<Discount> canDiscount(Sku sku, CurrentUser currentUser) {
        return this.canDiscount(sku.getProduct().getId(), currentUser);
    }

    private Optional<Discount> findDiscount(Sku sku, CurrentUser currentUser) {
        return this.canDiscount(sku.getProduct().getId(), currentUser);
    }

}
