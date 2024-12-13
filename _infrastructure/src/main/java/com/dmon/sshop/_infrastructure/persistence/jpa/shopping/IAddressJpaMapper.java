package com.dmon.sshop._infrastructure.persistence.jpa.shopping;

import com.dmon.sshop._domain.shopping.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAddressJpaMapper extends JpaRepository<Address, String> {
}
