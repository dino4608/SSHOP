package com.dino.backend.features.identity.application.impl;

import com.dino.backend.features.identity.application.ITokenQueryService;
import com.dino.backend.features.identity.domain.Token;
import com.dino.backend.infrastructure.aop.AppException;
import com.dino.backend.infrastructure.aop.ErrorCode;
import com.dino.backend.features.identity.domain.repository.ITokenDomainRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class TokenQueryServiceImpl implements ITokenQueryService {

    ITokenDomainRepository tokenDomainRepository;

    @Override
    public Token getById(String userId) {
        Token token = this.tokenDomainRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.TOKEN__NOT_FOUND));

        return token;
    }
}

