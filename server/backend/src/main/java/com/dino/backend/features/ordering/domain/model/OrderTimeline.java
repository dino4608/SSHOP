package com.dino.backend.features.ordering.domain.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderTimeline {
    Instant paymentDate;
    Instant orderDate;
    Instant shipmentDate;
    Instant deliveryDate;
}
