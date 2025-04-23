package com.dino.backend.infrastructure.persistent.product;

import com.dino.backend.features.product.application.model.projection.ProductProj;
import com.dino.backend.features.product.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IProductInfraRepository extends JpaRepository<Product, String>, JpaSpecificationExecutor<Product> {
    //LIST//
    Page<ProductProj> findAllProjectedBy(Pageable pageable);
}
