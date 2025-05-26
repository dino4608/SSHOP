package com.dino.backend.features.productcatalog.application;

import org.springframework.data.domain.Pageable;

import com.dino.backend.features.productcatalog.application.model.ProductRes;
import com.dino.backend.features.productcatalog.domain.model.ProductProjection;
import com.dino.backend.infrastructure.web.model.CurrentUser;
import com.dino.backend.shared.utils.PageRes;

public interface IProductService {
    // QUERY //

    PageRes<ProductProjection> list(Pageable pageable);

    ProductRes getById(String productId, CurrentUser currentUser);
}
