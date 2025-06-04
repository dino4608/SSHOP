package com.dino.backend.features.productcatalog.application.impl;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dino.backend.features.productcatalog.application.IProductService;
import com.dino.backend.features.productcatalog.application.mapper.IProductMapper;
import com.dino.backend.features.productcatalog.application.model.ProductItemRes;
import com.dino.backend.features.productcatalog.application.model.ProductRes;
import com.dino.backend.features.productcatalog.domain.repository.IProductRepository;
import com.dino.backend.features.promotion.application.IDiscountService;
import com.dino.backend.shared.api.model.CurrentUser;
import com.dino.backend.shared.application.utils.Id;
import com.dino.backend.shared.application.utils.PageRes;
import com.dino.backend.shared.domain.exception.AppException;
import com.dino.backend.shared.domain.exception.ErrorCode;

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
        var products = page.getContent().stream()
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
        Id id = Id.from(productId)
                .orElseThrow(() -> new AppException((ErrorCode.PRODUCT__NOT_FOUND)));

        var product = this.productRepository.findEagerById(id.value())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT__NOT_FOUND));

        var discount = this.discountService.canApply(id.value(), currentUser);

        var res = this.productMapper.toProductRes(product);
        res.setDiscount(discount.orElse(null));
        return res;

    }
}
