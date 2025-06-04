package com.dino.backend.features.productcatalog.application;

import com.dino.backend.features.productcatalog.domain.model.CategoryProjection;
import com.dino.backend.features.productcatalog.domain.Category;

import java.util.List;

public interface ICategoryService {
    // READ //
    List<CategoryProjection> getTree();

    // HELP //
    Category findOrErrorById(String categoryId);
}
