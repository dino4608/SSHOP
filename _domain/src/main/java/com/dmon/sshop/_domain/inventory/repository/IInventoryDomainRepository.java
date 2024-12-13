package com.dmon.sshop._domain.inventory.repository;

import com.dmon.sshop._domain.inventory.model.entity.Inventory;

import java.util.Optional;

public interface IInventoryDomainRepository {
    Optional<Inventory> findById(String id);
}
