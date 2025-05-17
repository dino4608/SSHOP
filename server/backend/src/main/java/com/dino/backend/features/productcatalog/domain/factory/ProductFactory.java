package com.dino.backend.features.productcatalog.domain.factory;

import com.dino.backend.features.productcatalog.domain.Product;
import com.dino.backend.features.productcatalog.domain.Sku;
import com.dino.backend.features.productcatalog.domain.model.ProductStatusType;
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
public class ProductFactory {

    SkuFactory skuAggFactory;

    //CREATE//
    public Product create(Product product) {
        product.setStatus(ProductStatusType.REVIEWING.name());

        product.setSlug(this.genSlug(product.getName()));

        product.setSkus(this.skuAggFactory.create(product)); //cascade

        //product.setRetailPrice(this.genRetailPrice(product.getSkus()));

        return product;
    }

    //FIELDS//
    public float genRetailPrice(List<Sku> skus) {
        float retailPrice = (float) skus.stream().parallel()
                .mapToDouble(Sku::getRetailPrice)
                .average()
                .orElse(0.0);

        return retailPrice;
    }

    public String genSlug(String name) {
        String slug = AppUtils.toSlug(name);

        return slug;
    }
}
