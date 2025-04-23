package com.dino.backend.features.product.application;

import com.dino.backend.features.product.application.model.projection.ProductProj;
import com.dino.backend.features.product.application.model.request.ProductReq;
import com.dino.backend.features.product.domain.entity.Product;
import com.dino.backend.shared.model.PageRes;
import org.springframework.data.domain.Pageable;

public interface IProductAppService {
    //CREATE//
    Product create(ProductReq.Create productDto);

    //LIST//
    PageRes<ProductProj> findAll(Pageable pageable);

}
