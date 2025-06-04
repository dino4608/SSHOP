package com.dino.backend.features.identity.domain.repository;

import com.dino.backend.features.identity.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;

import java.util.Optional;

public interface ITokenRepository extends JpaRepository<Token, Long> {
    // QUERY //

    Optional<Token> findByUserId(@Nullable Long userId);

    // COMMAND //
}
