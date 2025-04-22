package com.dino.backend.features.inventory.domain.factory;

import com.dino.backend.features.inventory.domain.entity.Sku;
import com.dino.backend.features.product.domain.entity.Product;
import com.dino.backend.shared.utils.AppUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class SkuFactory {
    InventoryFactory invFactory;

    // CREATE//
    public List<Sku> create(Product product) {
        List<Sku> skus = product.getSkus();
        skus.stream().parallel()
                .forEach(sku -> {
                    sku.setProduct(product);

                    sku.setStatus(Sku.StatusType.LIVE.name());

                    if (AppUtils.isEmpty(sku.getSkuCode()))
                        sku.setSkuCode(this.genSkuCode());

                    sku.setCarts(0);

                    sku.setInventory(this.invFactory.create(sku)); // cascade
                });

        return skus;
    }

    // FIELDS//
    public String genSkuCode() {
        String skuCode = AppUtils.genUUID();

        return skuCode;
    }
}
