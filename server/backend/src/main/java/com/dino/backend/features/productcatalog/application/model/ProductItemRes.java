package com.dino.backend.features.productcatalog.application.model;

import java.time.Instant;

import org.springframework.lang.Nullable;

import com.dino.backend.features.productcatalog.domain.model.ProductMeta;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductItemRes {

    String id;
    String status;
    Instant updatedAt;
    String name;
    String thumb;
    ProductMeta meta;

    Integer retailPrice;
    @Nullable
    Integer dealPrice;
    @Nullable
    Integer discountPercent;
}
