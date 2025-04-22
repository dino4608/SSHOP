package com.dino.backend.infrastructure.persistent.product;

import com.dino.backend.features.inventory.domain.entity.Sku;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ISkuInfraRepository extends JpaRepository<Sku, String> {
    // FIND//
    Optional<Sku> findBySkuCode(String skuCode);
}
