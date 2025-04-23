package com.dino.backend.features.product.domain.factory;

import com.dino.backend.features.inventory.domain.entity.Sku;
import com.dino.backend.features.inventory.domain.factory.SkuFactory;
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
public class ProductFactory {

    SkuFactory skuAggFactory;
    ProductMetricFactory metricAggFactory;

    //CREATE//
    public Product create(Product product) {
        product.setStatus(Product.StatusType.REVIEWING.name());

        product.setSlug(this.genSlug(product.getName()));

        product.setSkus(this.skuAggFactory.create(product)); //cascade

        product.setRetailPrice(this.genRetailPrice(product.getSkus()));

        product.setMetric(this.metricAggFactory.create(product));

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
