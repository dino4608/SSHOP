package com.dino.backend.features.productcatalog.domain.repository;

import com.dino.backend.features.productcatalog.domain.Sku;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ISkuInfraRepository extends JpaRepository<Sku, String> {
    // FIND//
    Optional<Sku> findByCode(String skuCode);
}
