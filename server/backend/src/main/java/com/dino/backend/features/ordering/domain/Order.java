package com.dino.backend.features.ordering.domain;

import com.dino.backend.features.identity.domain.User;
import com.dino.backend.features.ordering.domain.model.*;
import com.dino.backend.features.shop.domain.Shop;
import com.dino.backend.shared.domain.exception.AppException;
import com.dino.backend.shared.domain.exception.ErrorCode;
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

import java.time.Instant;
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
    OrderStatus status;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    OrderTimeline timeline;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    CheckoutSnapshot checkoutSnapshot;

    @Enumerated(EnumType.STRING)
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

    // FACTORY METHOD //

    /**
     * createDraftOrder.
     */
    public static Order createDraftOrder(
            List<OrderItem> orderItems, User buyer, Shop shop,
            CheckoutSnapshot checkoutSnapshot, ShippingDetail shippingDetail
    ) {
        Order order = new Order();
        order.setBuyer(buyer);
        order.setShop(shop);
        order.setCheckoutSnapshot(checkoutSnapshot);
        order.setShippingDetail(shippingDetail);
        order.setOrderItems(orderItems);
        order.setStatus(OrderStatus.DRAFT);

        // link OrderItems to Order
        orderItems.forEach(item -> item.setOrder(order));

        return order;
    }

    // --- Instance Update Methods ---

    /**
     * markAsPending
     */
    public void markAsPending() {
        if (this.status != OrderStatus.DRAFT && this.status != OrderStatus.UNPAID)
            throw new AppException(ErrorCode.SYSTEM__DEVELOPING_FEATURE); // TODO: SYSTEM__DEVELOPING_FEATURE

        this.setStatus(OrderStatus.PENDING);

        // TODO:
        // address
        // timeline ...
    }

    /**
     * Marks the order as UNPAID.
     * Can only transition from DRAFT or CANCELED (if re-opened).
     */
    public void markAsUnpaid() {
        if (this.status != OrderStatus.DRAFT && this.status != OrderStatus.CANCELED) {
            throw new IllegalStateException("Order status cannot be changed to UNPAID from " + this.status);
        }
        this.setStatus(OrderStatus.UNPAID);
        // Additional logic like updating timeline.paymentDate if needed
    }

    /**
     * Updates the payment method for the order.
     * Can only be called in DRAFT or UNPAID states.
     */
    public void updatePaymentMethod(PaymentMethod method) {
        if (this.status != OrderStatus.DRAFT && this.status != OrderStatus.UNPAID) {
            throw new IllegalStateException("Payment method can only be updated for DRAFT or UNPAID orders.");
        }
        if (method == null) {
            throw new IllegalArgumentException("Payment method cannot be null.");
        }
        this.setPaymentMethod(method);
    }

    /**
     * Updates shipping and delivery details for the order.
     * Can only be called in DRAFT or UNPAID states.
     */
    public void updateShippingDetails(ShippingDetail shippingDetail, OrderAddress deliveryAddress) {
        if (this.status != OrderStatus.DRAFT && this.status != OrderStatus.UNPAID) {
            throw new IllegalStateException("Shipping details can only be updated for DRAFT or UNPAID orders.");
        }
        if (shippingDetail == null) {
            throw new IllegalArgumentException("Shipping details cannot be null.");
        }
        if (deliveryAddress == null) {
            throw new IllegalArgumentException("Delivery address cannot be null.");
        }
        this.setShippingDetail(shippingDetail);
        this.setDeliveryAddress(deliveryAddress);
        // pickupAddress might be null if not pick-up
        // this.setPickupAddress(pickupAddress);
    }

    // ... (other update methods for status transitions like markAsPreparing, markAsDelivered, etc.)


}
