package com.dino.backend.features.identity.application.impl;

import com.dino.backend.features.identity.application.ITokenAppService;
import com.dino.backend.features.identity.application.ITokenQueryService;
import com.dino.backend.features.identity.domain.Token;
import com.dino.backend.features.identity.domain.repository.ITokenDomainRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class TokenAppServiceImpl implements ITokenAppService {

    ITokenDomainRepository tokenDomainRepository;

    ITokenQueryService tokenQueryService;

    @Override
    public void updateRefreshToken(String REFRESH_TOKEN, Instant refreshExpDate, String userId) {
        Token token = this.tokenQueryService.getById(userId);

        token = Token.updateRefreshToken(token, REFRESH_TOKEN, refreshExpDate);

        this.tokenDomainRepository.save(token);
    }
}

