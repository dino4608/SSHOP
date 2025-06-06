package com.dino.backend.features.productcatalog.application.model;

import java.util.List;

public record SkuLean(
        Long id,
        String code,
        List<Integer> tierOptionIndexes,
        String tierOptionValue) {
}