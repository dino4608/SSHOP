package com.dino.backend.features.userprofile.domain.repository;

import com.dino.backend.features.identity.domain.User;
import com.dino.backend.features.userprofile.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IAddressDomainRepository extends JpaRepository<Address, String> {
    // QUERY //
    Optional<Address> findByUserAndIsDefault(User user, Boolean isDefault);
}
