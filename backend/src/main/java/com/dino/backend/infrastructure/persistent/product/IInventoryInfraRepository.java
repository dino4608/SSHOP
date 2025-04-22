package com.dino.backend.infrastructure.persistent.product;

import com.dino.backend.features.inventory.domain.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IInventoryInfraRepository extends JpaRepository<Inventory, String> {
}
