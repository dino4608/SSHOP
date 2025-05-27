package com.dino.backend.features.productcatalog.application.impl;

import com.dino.backend.features.productcatalog.domain.model.CategoryProjection;
import com.dino.backend.features.productcatalog.application.ICategoryService;
import com.dino.backend.features.productcatalog.domain.Category;
import com.dino.backend.infrastructure.aop.AppException;
import com.dino.backend.infrastructure.aop.ErrorCode;
import com.dino.backend.features.productcatalog.domain.repository.ICategoryRepository;
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
public class CategoryServiceImpl implements ICategoryService {

    ICategoryRepository categoryRepository;

    // READ //
    @Override
    public List<CategoryProjection> getTree() {
        List<CategoryProjection> categories = this.categoryRepository.findAllProjectedBy(
                Sort.by(Sort.Direction.ASC, "position"),
                CategoryProjection.class);
        return categories;
    }

    // HELP //
    @Override
    public Category findOrErrorById(String cateId) {
        Category category = this.categoryRepository.findById(cateId)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY__NOT_FOUND));
        return category;
    }

}
