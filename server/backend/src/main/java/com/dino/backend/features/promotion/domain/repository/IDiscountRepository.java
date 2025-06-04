package com.dino.backend.features.promotion.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;

import com.dino.backend.features.promotion.domain.Discount;

public interface IDiscountRepository
        extends JpaRepository<Discount, Long>, JpaSpecificationExecutor<Discount> {

    List<Discount> findByProductId(@NonNull Long productId);

    @EntityGraph(attributePaths = { "discount" })
    List<Discount> findEagerByProductId(@NonNull Long productId);
}
