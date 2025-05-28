package com.dino.backend.features.promotion.application.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import com.dino.backend.features.promotion.application.IDiscountService;
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

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class DiscountServiceImpl implements IDiscountService {

    IDiscountRepository discountRepository;

    // QUERY //

    // canApply to product //
    @Override
    public Optional<Discount> canApply(String productId, @Nullable CurrentUser currentUser) {
        Id id = Id.from(productId).orElseThrow(() -> new AppException(ErrorCode.PRODUCT__NOT_FOUND));

        var discounts = this.discountRepository.findByProductId(id.value());

        return this.canApply(discounts, currentUser);
    }

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
}
