package com.dino.backend.features.productcatalog.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.dino.backend.features.productcatalog.application.model.ProductRes;
import com.dino.backend.features.productcatalog.domain.Product;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IProductMapper {
    ProductRes toProductRes(Product product);
}
