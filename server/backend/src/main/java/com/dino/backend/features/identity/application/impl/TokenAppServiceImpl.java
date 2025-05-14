package com.dino.backend.features.identity.application.impl;

import com.dino.backend.features.identity.application.ITokenAppService;
import com.dino.backend.features.identity.domain.Token;
import com.dino.backend.features.identity.domain.repository.ITokenDomainRepository;
import com.dino.backend.infrastructure.aop.AppException;
import com.dino.backend.infrastructure.aop.ErrorCode;
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

    // QUERY //

    // getById //
    @Override
    public Token getById(String userId) {
        return this.tokenDomainRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.TOKEN__FIND_FAILED));
    }

    // COMMAND //

    // updateRefreshToken //
    @Override
    public void updateRefreshToken(String refreshToken, Instant refreshTokenExpiry, String userId) {
        Token token = this.getById(userId);

        token = Token.updateRefreshToken(token, refreshToken, refreshTokenExpiry);

        this.tokenDomainRepository.save(token);
    }

    // isValidRefreshToken //
    @Override
    public boolean isRefreshTokenValid(String refreshToken, String userId) {
        Token token = this.getById(userId);

        return refreshToken.equals(token.getRefreshToken());
    }
}

