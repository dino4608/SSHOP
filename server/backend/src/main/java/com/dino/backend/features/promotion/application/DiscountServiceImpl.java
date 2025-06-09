package com.dino.backend.features.promotion.application;

import com.dino.backend.features.productcatalog.domain.Product;
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
    private Optional<Discount> canApply(List<Discount> discounts, @Nullable CurrentUser currentUser) {
        var discountsCanApply = discounts.stream()
                .filter(dp -> dp.canApply(currentUser))
                .toList();

        if (discountsCanApply.isEmpty())
            return Optional.empty();

        if (discountsCanApply.size() == 1)
            return Optional.of(discountsCanApply.getFirst());

        return discountsCanApply.stream()
                .min(Comparator.comparingInt(dp -> dp.getDiscountProgram().getPriority()));
    }

    @Override
    public Optional<Discount> canDiscount(Product product) {
        var discounts = this.discountRepository.findByProductId(product.getId());

        return this.canApply(discounts, null);
    }

    // canApply to product //
    @Override
    public Optional<Discount> canDiscount(Product product, CurrentUser currentUser) {
        var discounts = this.discountRepository.findByProductId(product.getId());

        return this.canApply(discounts, currentUser);
    }

    // canApply to Sku //
    @Override
    public Optional<Discount> canDiscount(Sku sku, CurrentUser currentUser) {
        return this.canDiscount(sku.getProduct(), currentUser);
    }
}
