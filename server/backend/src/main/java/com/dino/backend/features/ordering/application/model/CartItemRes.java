package com.dino.backend.features.ordering.application.model;

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
public class CartItemRes {

    Long skuId;
    String skuCode;
    String skuTierOptionValue;
    Integer retailPrice;
    int quantity;
    String productName;
    String productThumb;
}