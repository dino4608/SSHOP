package com.dmon.sshop._domain.inventory.service;

import com.dmon.sshop._domain.inventory.model.entity.Inventory;

public interface IInventoryDomainService {
    //HELPER//
    Inventory findOrError(String invId);
}
