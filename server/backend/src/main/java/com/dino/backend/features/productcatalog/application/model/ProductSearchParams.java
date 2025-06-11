package com.dino.backend.features.productcatalog.application.model;

import java.util.List;

public record ProductSearchParams(
        String keyword,
        List<Integer> categories,
        Integer[] priceRange
) {
}
