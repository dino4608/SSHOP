package com.dino.backend.features.productcatalog.domain.factory;

import com.dino.backend.features.inventory.domain.factory.InventoryFactory;
import com.dino.backend.features.productcatalog.domain.Sku;
import com.dino.backend.features.productcatalog.domain.Product;
import com.dino.backend.features.productcatalog.domain.model.SkuStatusType;
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

                    sku.setStatus(SkuStatusType.LIVE.name());

                    if (AppUtils.isEmpty(sku.getSkuCode()))
                        sku.setSkuCode(this.genSkuCode());

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
