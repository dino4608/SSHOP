package com.dino.backend.features.ordering.domain;

import com.dino.backend.features.identity.domain.User;
import com.dino.backend.features.shop.domain.Shop;
import com.dino.backend.features.userprofile.domain.Address;
import com.dino.backend.shared.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.*;

import java.time.Instant;
import java.util.ArrayList;

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
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "orderId", nullable = false, updatable = false)
    String id;

    String status;

    int count;

    float subtotal;

    float shippingFee;

    float discount; // seller discount + platform discount

    float shippingDiscount;

    float total;

    Instant orderDate;

    String paymentMethod;

    Instant paymentTime;

    Instant shipmentDate;

    Instant deliveryDate;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    Address address;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    ArrayList<OrderItem> items;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyerId", updatable = false, nullable = false)
    @JsonIgnore
    User buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopId", updatable = false, nullable = false)
    @JsonIgnore
    Shop shop;

    //NESTED OBJECTS//
    public enum StatusType {DRAFT, UNPAID, PREPARING, TRANSIT, DELIVERING, DELIVERED, RETURN, CANCELED,}

    public enum PaymentMethodType {COD, ZALOPAY, MONO, VNPAY,}

}
