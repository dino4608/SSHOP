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

    // --- Static Factory Method ---
    public static OrderTimeline createInitialTimeline(Instant orderDate) {
        if (orderDate == null) {
            throw new IllegalArgumentException("Order date cannot be null for initial timeline.");
        }
        return new OrderTimeline(null, orderDate, null, null);
    }
}
