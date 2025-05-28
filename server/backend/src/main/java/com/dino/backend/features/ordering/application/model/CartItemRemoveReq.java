package com.dino.backend.features.ordering.application.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemRemoveReq {

    @NotEmpty(message = "Cart item IDs cannot be empty")
    @Size(min = 1, message = "At least one cart item ID is required")
    List<Long> cartItemIds;
}