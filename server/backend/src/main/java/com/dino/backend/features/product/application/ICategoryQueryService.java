package com.dino.backend.features.product.application;

import com.dino.backend.features.product.application.model.projection.CategoryProjection;
import com.dino.backend.features.product.domain.entity.Category;

import java.util.List;

public interface ICategoryQueryService {
    // READ //
    List<CategoryProjection> getTree();

    // HELP //
    Category findOrErrorById(String cateId);
}
