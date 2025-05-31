package com.dino.backend.features.identity.application.impl;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.dino.backend.features.identity.application.ITokenService;
import com.dino.backend.features.identity.domain.Token;
import com.dino.backend.features.identity.domain.repository.ITokenRepository;
import com.dino.backend.shared.domain.exception.AppException;
import com.dino.backend.shared.domain.exception.ErrorCode;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class TokenServiceImpl implements ITokenService {

    ITokenRepository tokenRepository;

    // QUERY //

    // getByUser //
    @Override
    public Token getByUserId(Long userId) {
        return this.tokenRepository.findByUserId(userId)
                .orElseThrow(() -> new AppException(ErrorCode.TOKEN__FIND_FAILED));
    }

    // COMMAND //

    // updateRefreshToken //
    @Override
    public void updateRefreshToken(String refreshToken, Instant refreshTokenExpiry, Long userId) {
        Token token = this.getByUserId(userId);

        Token.updateRefreshToken(token, refreshToken, refreshTokenExpiry);
        this.tokenRepository.save(token);
    }

    // isValidRefreshToken //
    @Override
    public boolean isRefreshTokenValid(String refreshToken, Long userId) {
        Token token = this.getByUserId(userId);

        return refreshToken.equals(token.getRefreshToken());
    }
}
