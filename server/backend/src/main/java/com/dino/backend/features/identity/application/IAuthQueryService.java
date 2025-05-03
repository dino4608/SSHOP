package com.dino.backend.features.identity.application;

import com.dino.backend.features.identity.application.model.LookupIdentifierResponse;
import com.dino.backend.features.identity.domain.User;

import java.util.Optional;

public interface IAuthQueryService {

    LookupIdentifierResponse lookupIdentifier(String email);

    // TODO: use Optional<>, or @Nullable to avoid Null pointer exception: Optional<User> getUserByIdentifier(String identifier);
    Optional<User> findUserByIdentifier(String email);
}
