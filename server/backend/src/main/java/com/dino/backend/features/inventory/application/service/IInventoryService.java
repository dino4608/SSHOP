package com.dino.backend.features.inventory.application.service;

import com.dino.backend.features.inventory.domain.Inventory;

public interface IInventoryService {
    /**
     * checkStock:
     */
    Inventory checkStock(Long skuId, int quantity);

    /**
     * reserveStock
     */
    void reserveStock(Long skuId, int quantity);
}
