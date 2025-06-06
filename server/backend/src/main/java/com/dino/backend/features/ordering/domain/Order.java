package com.dino.backend.features.ordering.domain;

import com.dino.backend.features.identity.domain.User;
import com.dino.backend.features.ordering.domain.model.*;
import com.dino.backend.features.shop.domain.Shop;
import com.dino.backend.shared.domain.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.*;

import java.util.List;

@Entity
@Table(name = "orders")
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "UPDATE orders SET is_deleted = true WHERE order_id=?")
@SQLRestriction("is_deleted = false")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "order_id")
    Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    OrderStatus status;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    OrderTimeline timeline;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    CheckoutSnapshot checkoutSnapshot;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    PaymentMethod paymentMethod;

    @Column(columnDefinition = "text")
    private String note;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    ShippingDetail shippingDetail;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    OrderAddress deliveryAddress;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    OrderAddress pickupAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id", nullable = false, updatable = false)
    @JsonIgnore
    User buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false, updatable = false)
    @JsonIgnore
    Shop shop;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<OrderItem> orderItems;

}
