package com.dino.backend.features.ordering.domain.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderAddress {

    String contactName;

    String contactPhone;

    String province;

    String district;

    String commune;

    String street;
}
