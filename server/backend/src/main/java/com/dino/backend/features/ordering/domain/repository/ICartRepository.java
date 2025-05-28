package com.dino.backend.features.ordering.domain.repository;

import com.dino.backend.features.ordering.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface ICartRepository extends JpaRepository<Cart, String> {

    @EntityGraph(attributePaths = { "cartItems", "cartItems.sku" })
    Optional<Cart> findJoinAllById(@NonNull String id);

    @EntityGraph(attributePaths = { "cartItems" })
    Optional<Cart> findJoinItemsById(@NonNull String id);
}