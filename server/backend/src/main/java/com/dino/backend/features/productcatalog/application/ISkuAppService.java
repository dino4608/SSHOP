package com.dino.backend.features.productcatalog.application;

import com.dino.backend.features.productcatalog.domain.Sku;

public interface ISkuAppService {
    //SERVICES//

    /**
     * @param skuCode String
     * @desc check if the value of field is unique.
     */
    void checkSkuCodeOrError(String skuCode);

    Sku findOrError(String skuId);
}
