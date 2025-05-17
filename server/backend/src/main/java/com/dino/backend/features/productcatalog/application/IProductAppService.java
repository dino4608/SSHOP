package com.dino.backend.features.productcatalog.application;

import com.dino.backend.features.productcatalog.domain.model.ProductProjection;
import com.dino.backend.shared.model.PageRes;
import org.springframework.data.domain.Pageable;

public interface IProductAppService {
    // QUERY //

    PageRes<ProductProjection> list(Pageable pageable);

    // COMMAND //

}
