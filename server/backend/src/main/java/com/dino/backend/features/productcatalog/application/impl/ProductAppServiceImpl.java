package com.dino.backend.features.productcatalog.application.impl;

import com.dino.backend.features.productcatalog.application.mapper.IProductMapper;
import com.dino.backend.features.productcatalog.application.model.projection.ProductProj;
import com.dino.backend.features.productcatalog.application.model.request.ProductReq;
import com.dino.backend.features.productcatalog.application.ICategoryQueryService;
import com.dino.backend.features.productcatalog.application.IProductAppService;
import com.dino.backend.features.productcatalog.application.ISkuAppService;
import com.dino.backend.features.productcatalog.domain.Product;
import com.dino.backend.features.productcatalog.domain.factory.ProductFactory;
import com.dino.backend.features.productcatalog.domain.repository.IProductInfraRepository;
import com.dino.backend.shared.model.PageRes;
import com.dino.backend.shared.utils.AppUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ProductAppServiceImpl implements IProductAppService {
    IProductInfraRepository productDomainRepo;

    IProductMapper productMapper;

    ProductFactory productAggFactory;

    ICategoryQueryService cateDomainService;

    ISkuAppService skuDomainService;

    //CREATE//
    @Override
    public Product create(ProductReq.Create productDto) {
        Product productRequested = this.productMapper.toEntity(productDto);

        // productRequested.setShop(Shop.builder().id(sellerId).build()); // todo: security utils

        productRequested.setCategory(this.cateDomainService.findOrErrorById(productRequested.getCategory().getId()));

        productRequested.getSkus().stream().parallel()
                .forEach(sku -> {
                    if (AppUtils.isPresent(sku.getSkuCode()))
                        this.skuDomainService.checkSkuCodeOrError(sku.getSkuCode());
                });

        final Product productCreated = this.productAggFactory.create(productRequested);

        Product productResult = this.productDomainRepo.save(productCreated);
        return productResult;
    }

    //LIST//
    public PageRes<ProductProj> findAll(Pageable pageable) {
        Page<ProductProj> productPage = this.productDomainRepo.findAllProjectedBy(pageable);

        return AppUtils.toPageRes(productPage);
    }
}
