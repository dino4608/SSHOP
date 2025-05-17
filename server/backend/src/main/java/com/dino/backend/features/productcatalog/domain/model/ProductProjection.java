package com.dino.backend.features.productcatalog.domain.model;

import java.time.Instant;

public interface ProductProjection {
    String getId();
    String getStatus();
    Instant getUpdatedAt();
    String getName();
    String getThumb();
    Integer getRetailPrice();
    ProductMeta getMeta();

//    List<ISkuProjection> getSkus();

//    interface ISkuProjection {
//        String getStatus();
//        String getSkuCode();
//        Integer[] getTierIndex();
//        Integer getRetailPrice();
//        IInventoryProjection getInventory();
//    }

//    interface IInventoryProjection {
//        Integer getStocks();
//    }
}
