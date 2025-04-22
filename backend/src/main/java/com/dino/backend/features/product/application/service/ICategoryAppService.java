package com.dino.backend.features.product.application.service;

import com.dino.backend.features.product.application.model.projection.CategoryProj;
import com.dino.backend.features.product.application.model.request.CategoryReq;
import com.dino.backend.features.product.domain.entity.Category;
import com.dino.backend.shared.model.PageRes;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICategoryAppService {
    //CREATE//
    Category create(CategoryReq.Create cateDto);

    //UPDATE//
    Category update(CategoryReq.Update cateDto, String cateId);

    //LIST//
    PageRes<CategoryProj> findAll(Pageable pageable);
    List<CategoryProj> findTree();

    //FIND//
    Category find(String cateId);

    //DELETE//
    Void delete(String cateId);

    //SERVICE//
    Category findOrError(String cateId);
}
