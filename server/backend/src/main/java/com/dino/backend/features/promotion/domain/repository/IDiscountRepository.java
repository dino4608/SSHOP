package com.dino.backend.features.promotion.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;

import com.dino.backend.features.promotion.domain.Discount;

public interface IDiscountRepository
        extends JpaRepository<Discount, String>, JpaSpecificationExecutor<Discount> {

    List<Discount> findByProductId(@NonNull String productId);

    @EntityGraph(attributePaths = { "discount" })
    List<Discount> findEagerByProductId(@NonNull String productId);
}
