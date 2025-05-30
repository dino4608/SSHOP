package com.dino.backend.features.ordering.domain;

import com.dino.backend.features.identity.domain.User;
import com.dino.backend.features.productcatalog.domain.Sku;
import com.dino.backend.infrastructure.aop.AppException;
import com.dino.backend.infrastructure.aop.ErrorCode;
import com.dino.backend.shared.model.BaseEntity;
import com.dino.backend.shared.utils.DeleteRes;
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

    int total;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id", nullable = false, updatable = false)
    @JsonIgnore
    User buyer;

    @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    // @OrderBy("id ASC")
    List<CartItem> cartItems;

    // SETTER METHODS //

    public void setTotal(int total) {
        if (total < 0)
            throw new AppException(ErrorCode.CART__TOTAL_MIN_INVALID);
        if (total > 100)
            throw new AppException(ErrorCode.CART__TOTAL_MAX_INVALID);

        this.total = total;
    }

    // STATIC METHODS //

    public static Cart createCart(User buyer) {
        Cart cart = new Cart();
        cart.setCartItems(new ArrayList<>());
        cart.setTotal(0);
        cart.setBuyer(buyer);

        return cart;
    }

    public static CartItem addCartItem(Cart cart, Sku sku, int quantity) {
        CartItem item = new CartItem();
        item.setQuantity(quantity);
        item.setCart(cart);
        item.setSku(sku);

        cart.getCartItems().add(item);
        cart.setTotal(cart.getTotal() + 1);

        return item;
    }

    public static DeleteRes removeCartItems(Cart cart, List<Long> skuIds) {
        // NOTE: orphanRemoval
        // 1. filter CartItems (objects on memory) to remove
        var cartItemsToRemove = cart.getCartItems().stream()
                .filter(cartItem -> skuIds.contains(cartItem.getSku().getId()))
                .toList();

        // 2. removeAll items => JPA note they are orphan => orphanRemoval auto delete
        cart.getCartItems().removeAll(cartItemsToRemove);
        cart.setTotal(cart.getTotal() - cartItemsToRemove.size());

        return DeleteRes.success();
    }

    public static void increaseQuantity(CartItem item, int increment) {
        item.setQuantity(item.getQuantity() + increment);
    }

    public static void updateQuantity(CartItem item, int quantity) {
        item.setQuantity(quantity);
    }
}
