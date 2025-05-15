package com.dino.backend.features.inventory.domain.factory;

import com.dino.backend.features.inventory.domain.Inventory;
import com.dino.backend.features.productcatalog.domain.Sku;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class InventoryFactory {
    //CREATE//
    public Inventory create(Sku sku) {
        Inventory inventory = Inventory.builder()
                .sku(sku)
                .total(sku.getInventory().getStocks())
                .stocks(sku.getInventory().getStocks())
                .sales(0)
                .build();

        return inventory;
    }
}
