package com.dino.backend.features.identity.application.impl;

import com.dino.backend.features.identity.application.IAuthQueryService;
import com.dino.backend.features.identity.application.model.LookupIdentifierRequest;
import com.dino.backend.features.identity.domain.User;
import com.dino.backend.features.identity.domain.repository.IUserDomainRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthQueryServiceImpl implements IAuthQueryService {

    IUserDomainRepository userDomainRepository;

    public LookupIdentifierRequest lookupIdentifier(String email) {
        Optional<User> userOpt = this.findUserByIdentifier(email);

        return LookupIdentifierRequest.builder()
                .isEmailProvided(userOpt.isPresent())
                .isPasswordProvided(userOpt.isPresent() && userOpt.get().getPassword() != null)
                .build();
    }

    @Override
    public Optional<User> findUserByIdentifier(String email) {
        if (email == null) {
            return Optional.empty();
        }

        return this.userDomainRepository.findByEmail(email);
    }
}

