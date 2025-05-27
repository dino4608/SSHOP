package com.dino.backend.features.productcatalog.application.impl;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dino.backend.features.productcatalog.application.IProductService;
import com.dino.backend.features.productcatalog.application.mapper.IProductMapper;
import com.dino.backend.features.productcatalog.application.model.ProductItemRes;
import com.dino.backend.features.productcatalog.application.model.ProductRes;
import com.dino.backend.features.productcatalog.domain.repository.IProductRepository;
import com.dino.backend.features.promotion.application.IDiscountService;
import com.dino.backend.infrastructure.aop.AppException;
import com.dino.backend.infrastructure.aop.ErrorCode;
import com.dino.backend.infrastructure.web.model.CurrentUser;
import com.dino.backend.shared.utils.Id;
import com.dino.backend.shared.utils.PageRes;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ProductServiceImpl implements IProductService {

    IDiscountService discountService;
    IProductRepository productRepository;
    IProductMapper productMapper;

    // QUERY //

    // list //
    @Override
    public PageRes<ProductItemRes> list(Pageable pageable) {
        var page = this.productRepository.findAllProjectedBy(pageable);
        var products = page.getContent().parallelStream()
                .map(p -> {
                    var product = this.productMapper.toProductItemRes(p);
                    var discount = this.discountService.canApply(p.getId(), null);
                    discount.ifPresent(d -> {
                        product.setDealPrice(
                                d.getDealPrice() != null ? d.getDealPrice() : d.getMinDealPrice());
                        product.setDiscountPercent(
                                d.getDiscountPercent() != null ? d.getDiscountPercent() : d.getMinDiscountPercent());
                    });
                    return product;
                })
                .toList();
        return PageRes.from(page, products);
    }

    // getById //
    @Override
    public ProductRes getById(String productId, CurrentUser currentUser) {
        Id.from(productId)
                .orElseThrow(() -> new AppException((ErrorCode.PRODUCT__NOT_FOUND)));

        var product = this.productRepository.findEagerById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT__NOT_FOUND));

        var discount = this.discountService.canApply(productId, currentUser);

        var res = this.productMapper.toProductRes(product);
        res.setDiscount(discount.isPresent() ? discount.get() : null);
        return res;

    }
}
