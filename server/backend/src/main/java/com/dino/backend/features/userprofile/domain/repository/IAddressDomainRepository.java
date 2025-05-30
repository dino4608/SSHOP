package com.dino.backend.features.userprofile.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dino.backend.features.userprofile.domain.Address;

public interface IAddressDomainRepository extends JpaRepository<Address, Long> {
    // QUERY //

    Optional<Address> findByUserIdAndIsDefault(Long userId, Boolean isDefault);
}
