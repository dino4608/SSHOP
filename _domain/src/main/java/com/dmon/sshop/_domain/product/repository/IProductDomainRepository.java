package com.dmon.sshop._domain.product.repository;

import com.dmon.sshop._domain.product.model.projection.ProductProj;
import com.dmon.sshop._domain.product.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductDomainRepository {
    //LIST//
    Page<ProductProj> findAllProjectedBy(Pageable pageable);

    //PERSIST//
    Product save(Product product);
}
