package com.dino.backend.features.product.domain.factory;

import com.dino.backend.features.product.domain.entity.Product;
import com.dino.backend.features.product.domain.entity.ProductMetric;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ProductMetricFactory {
    //CREATE//
    public ProductMetric create(Product product) {
        ProductMetric productMetric = ProductMetric.builder()
                .product(product)
                .sales(0)
                .views(0)
                .carts(0)
                .stocks(product.getSkus().stream().parallel()
                        .map(sku -> sku.getInventory().getStocks())
                        .reduce(0, Integer::sum)
                )
                .build();

        return productMetric;
    }
}
