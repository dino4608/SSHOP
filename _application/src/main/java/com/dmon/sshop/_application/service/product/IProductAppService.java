package com.dmon.sshop._application.service.product;

import com.dmon.sshop._domain.common.base.PageRes;
import com.dmon.sshop._domain.product.model.projection.ProductProj;
import com.dmon.sshop._domain.product.model.request.ProductReq;
import com.dmon.sshop._domain.product.model.entity.Product;
import org.springframework.data.domain.Pageable;

public interface IProductAppService {
    //CREATE//
    Product create(ProductReq.Create productDto);

    //LIST//
    PageRes<ProductProj> findAll(Pageable pageable);

    //FIND//
    Object find(String productId);
}
