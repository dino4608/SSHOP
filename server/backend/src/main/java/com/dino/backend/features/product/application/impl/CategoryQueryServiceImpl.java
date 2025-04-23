package com.dino.backend.features.product.application.impl;

import com.dino.backend.features.product.application.model.projection.CategoryProjection;
import com.dino.backend.features.product.application.ICategoryQueryService;
import com.dino.backend.features.product.domain.entity.Category;
import com.dino.backend.infrastructure.aop.AppException;
import com.dino.backend.infrastructure.aop.ErrorCode;
import com.dino.backend.infrastructure.persistent.product.ICategoryInfraRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CategoryQueryServiceImpl implements ICategoryQueryService {

    ICategoryInfraRepository categoryInfraRepo;

    // READ //
    @Override
    public List<CategoryProjection> getTree() {
        List<CategoryProjection> categories = this.categoryInfraRepo.findAllProjectedBy(
                Sort.by(Sort.Direction.ASC, "position"),
                CategoryProjection.class
        );
        return categories;
    }

    // HELP //
    @Override
    public Category findOrErrorById(String cateId) {
        Category category = this.categoryInfraRepo.findById(cateId)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY__NOT_FOUND));
        return category;
    }

}
