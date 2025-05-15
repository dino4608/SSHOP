package com.dino.backend.features.productcatalog.application;

import com.dino.backend.features.productcatalog.application.model.projection.ProductProj;
import com.dino.backend.features.productcatalog.application.model.request.ProductReq;
import com.dino.backend.features.productcatalog.domain.Product;
import com.dino.backend.shared.model.PageRes;
import org.springframework.data.domain.Pageable;

public interface IProductAppService {
    //CREATE//
    Product create(ProductReq.Create productDto);

    //LIST//
    PageRes<ProductProj> findAll(Pageable pageable);

}
