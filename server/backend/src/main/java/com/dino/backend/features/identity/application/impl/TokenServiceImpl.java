package com.dino.backend.features.identity.application.impl;

import com.dino.backend.features.identity.application.ITokenService;
import com.dino.backend.features.identity.domain.Token;
import com.dino.backend.features.identity.domain.repository.ITokenRepository;
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
public class TokenServiceImpl implements ITokenService {

    ITokenRepository tokenRepository;

    // QUERY //

    // getById //
    @Override
    public Token getById(String userId) {
        return this.tokenRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.TOKEN__FIND_FAILED));
    }

    // COMMAND //

    // updateRefreshToken //
    @Override
    public void updateRefreshToken(String refreshToken, Instant refreshTokenExpiry, String userId) {
        Token token = this.getById(userId);

        token = Token.updateRefreshToken(token, refreshToken, refreshTokenExpiry);

        this.tokenRepository.save(token);
    }

    // isValidRefreshToken //
    @Override
    public boolean isRefreshTokenValid(String refreshToken, String userId) {
        Token token = this.getById(userId);

        return refreshToken.equals(token.getRefreshToken());
    }
}
