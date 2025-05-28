package com.dino.backend.features.ordering.application.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
public class UpdateQuantityReq {

    @NotNull(message = "CART__SKU_EMPTY") // TODO
    Long cartItemId;

    @Min(value = 1, message = "CART__QUANTITY_MIN_INVALID")
    @Max(value = 100, message = "CART__QUANTITY_MAX_INVALID")
    int quantity;
}