package com.dino.backend.features.productcatalog.application.impl;

import com.dino.backend.features.productcatalog.application.IProductAppService;
import com.dino.backend.features.productcatalog.domain.Product;
import com.dino.backend.features.productcatalog.domain.model.ProductProjection;
import com.dino.backend.features.productcatalog.domain.repository.IProductInfraRepository;
import com.dino.backend.infrastructure.aop.AppException;
import com.dino.backend.infrastructure.aop.ErrorCode;
import com.dino.backend.shared.utils.Id;
import com.dino.backend.shared.utils.PageRes;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ProductAppServiceImpl implements IProductAppService {

    IProductInfraRepository productInfraRepository;

    // QUERY //

    // list //
    @Override
    public PageRes<ProductProjection> list(Pageable pageable) {
        return PageRes.from(this.productInfraRepository.findAllProjectedBy(pageable));
    }

    // getById //
    @Override
    public Product getById(String productId) {
        Id.from(productId)
                .orElseThrow(() -> new AppException((ErrorCode.PRODUCT__NOT_FOUND)));

        return this.productInfraRepository.findEagerById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT__NOT_FOUND));
    }
}
