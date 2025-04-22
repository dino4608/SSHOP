package com.dino.backend.features.product.application.mapper;

import com.dino.backend.features.product.application.model.request.CategoryReq;
import com.dino.backend.features.product.application.model.response.CategoryRes;
import com.dino.backend.features.product.domain.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICategoryMapper {
    Category toEntity(CategoryReq.Create payload);
    Category toEntity(CategoryReq.Update payload);
    CategoryRes toRes(Category category);
}
