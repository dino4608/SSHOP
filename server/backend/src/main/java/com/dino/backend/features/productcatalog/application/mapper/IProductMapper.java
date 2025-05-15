package com.dino.backend.features.productcatalog.application.mapper;

import com.dino.backend.features.productcatalog.application.model.request.ProductReq;
import com.dino.backend.features.productcatalog.application.model.response.ProductRes;
import com.dino.backend.features.productcatalog.domain.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IProductMapper {
    Product toEntity(ProductReq.Create productDto);
    ProductRes toRes(Product entity);
}
