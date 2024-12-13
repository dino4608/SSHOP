package com.dmon.sshop._infrastructure.persistence.jpa.product;

import com.dmon.sshop._domain.inventory.model.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IInventoryJpaMapper extends JpaRepository<Inventory, String> {
}
