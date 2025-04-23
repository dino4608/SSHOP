package com.dino.backend.features.product.application;

import com.dino.backend.features.inventory.domain.entity.Sku;

public interface ISkuAppService {
    //SERVICES//

    /**
     * @param skuCode String
     * @desc check if the value of field is unique.
     */
    void checkSkuCodeOrError(String skuCode);

    Sku findOrError(String skuId);
}
