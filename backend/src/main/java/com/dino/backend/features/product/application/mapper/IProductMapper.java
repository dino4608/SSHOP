package com.dino.backend.features.product.application.mapper;

import com.dino.backend.features.product.application.model.request.ProductReq;
import com.dino.backend.features.product.application.model.response.ProductRes;
import com.dino.backend.features.product.domain.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IProductMapper {
    Product toEntity(ProductReq.Create productDto);
    ProductRes toRes(Product entity);
}
