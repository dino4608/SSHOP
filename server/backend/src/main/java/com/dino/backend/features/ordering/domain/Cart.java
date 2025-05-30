package com.dino.backend.features.ordering.domain;

import com.dino.backend.features.identity.domain.User;
import com.dino.backend.features.productcatalog.domain.Sku;
import com.dino.backend.shared.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "UPDATE carts SET is_deleted = true WHERE cart_id=?")
@SQLRestriction("is_deleted = false")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Cart extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "cart_id")
    Long id;

    int count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id", nullable = false, updatable = false)
    @JsonIgnore
    User buyer;

    @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    // @OrderBy("id ASC")
    List<CartItem> cartItems;

    public static Cart createCart(User buyer) {
        Cart newCart = Cart.builder()
                .cartItems(new ArrayList<>())
                .count(0)
                .buyer(buyer)
                .build();

        return newCart;
    }

    public static void addCartItem(Cart cart, Sku sku, int quantity) {
        CartItem item = CartItem.builder()
                .quantity(quantity)
                .cart(cart)
                .sku(sku)
                .build();

        cart.getCartItems().add(item);
        cart.setCount(cart.getCount() + 1);
    }

    public static void removeCartItem(Cart cart, List<CartItem> cartItemsToRemove) {
        // NOTE: orphanRemoval
        // removeAll items => JPA note they are orphan => orphanRemoval auto delete
        cart.getCartItems().removeAll(cartItemsToRemove);
        cart.setCount(cart.getCount() - cartItemsToRemove.size());
    }

    public static void increaseQuantity(CartItem item, int increment) {
        item.setQuantity(item.getQuantity() + increment);
    }

    public static void decreaseQuantity(CartItem item, int decrement) {
        item.setQuantity(item.getQuantity() - decrement);
    }

    // TODO: setQuantity > 0
}
