package com.dino.backend.features.identity.domain.repository;

import com.dino.backend.features.identity.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITokenDomainRepository extends JpaRepository<Token, String> {
    // QUERY //

    // COMMAND //
}
