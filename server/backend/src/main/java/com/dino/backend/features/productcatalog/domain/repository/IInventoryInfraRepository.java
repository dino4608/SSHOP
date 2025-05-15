package com.dino.backend.features.productcatalog.domain.repository;

import com.dino.backend.features.inventory.domain.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IInventoryInfraRepository extends JpaRepository<Inventory, String> {
}
