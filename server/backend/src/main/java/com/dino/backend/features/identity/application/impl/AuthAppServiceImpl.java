package com.dino.backend.features.identity.application.impl;

import com.dino.backend.features.identity.application.IAuthAppService;
import com.dino.backend.features.identity.application.IAuthQueryService;
import com.dino.backend.features.identity.application.ITokenAppService;
import com.dino.backend.features.identity.application.IUserQueryService;
import com.dino.backend.features.identity.application.mapper.IUserMapper;
import com.dino.backend.features.identity.application.model.AuthResponse;
import com.dino.backend.features.identity.application.model.GoogleOauth2Request;
import com.dino.backend.features.identity.application.model.PasswordLoginRequest;
import com.dino.backend.features.identity.domain.User;
import com.dino.backend.features.identity.domain.repository.IUserDomainRepository;
import com.dino.backend.infrastructure.aop.AppException;
import com.dino.backend.infrastructure.aop.ErrorCode;
import com.dino.backend.infrastructure.security.IOauth2InfraProvider;
import com.dino.backend.infrastructure.security.ISecurityInfraProvider;
import com.dino.backend.infrastructure.security.model.GoogleTokenResponse;
import com.dino.backend.infrastructure.security.model.GoogleUserResponse;
import com.dino.backend.infrastructure.security.model.JwtType;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthAppServiceImpl implements IAuthAppService {

    ITokenAppService tokenAppService;

    IAuthQueryService authQueryService;

    IUserQueryService userQueryService;

    IUserDomainRepository userDomainRepository;

    IUserMapper userMapper;

    ISecurityInfraProvider securityInfraProvider;

    IOauth2InfraProvider oauth2InfraProvider;

    // licenseToken //
    private AuthResponse licenseToken(User user, HttpHeaders headers) {
        // get tokens
        String accessToken = this.securityInfraProvider.genToken(user, JwtType.ACCESS_TOKEN);
        String refreshToken = this.securityInfraProvider.genToken(user, JwtType.REFRESH_TOKEN);
        Duration refreshTokenTtl = this.securityInfraProvider.getTtl(JwtType.REFRESH_TOKEN);
        Instant refreshTokenExpiry = this.securityInfraProvider.getExpiry(JwtType.REFRESH_TOKEN);

        // update refresh token to database
        this.tokenAppService.updateRefreshToken(refreshToken, refreshTokenExpiry, user.getId());

        // set refresh token to cookie
        HttpCookie cookie = ResponseCookie.from(JwtType.REFRESH_TOKEN.name(), refreshToken)
                .httpOnly(true)
                .sameSite("None")
                .secure(true)
                .path("/")
                .maxAge(refreshTokenTtl)
                .build();
        headers.add(HttpHeaders.SET_COOKIE, cookie.toString());

        return AuthResponse.builder()
                .isAuthenticated(true)
                .accessToken(accessToken)
                .user(User.responseUser(user))
                .build();
    }

    // login + PasswordLoginRequest //
    @Override
    public AuthResponse login(PasswordLoginRequest request, HttpHeaders headers) {
        // get user
        User user = authQueryService.findUserByIdentifier(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.AUTH__IDENTIFIER_NOT_FOUND));

        // match password
        if (!this.securityInfraProvider.matchPassword(request.getPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.AUTH__PASSWORD_INVALID);
        }

        //  license token
        AuthResponse authResponse = this.licenseToken(user, headers);

        return authResponse;
    }

    // signup + PasswordLoginRequest //
    @Override
    public AuthResponse signup(PasswordLoginRequest request, HttpHeaders headers) {
        // don't exist user
        if (this.authQueryService.findUserByIdentifier(request.getEmail()).isPresent()) {
            throw new AppException(ErrorCode.AUTH__IDENTIFIER_EXISTED);
        }

        // create
        User user = User.createSignupUser(
                this.userMapper.toUser(request),
                this.securityInfraProvider.hashPassword(request.getPassword())
        );
        user = this.userDomainRepository.save(user);

        // license token
        AuthResponse authResponse = this.licenseToken(user, headers);

        return authResponse;
    }

    // loginOrSignup + GoogleOauth2Request //
    @Override
    public AuthResponse loginOrSignup(GoogleOauth2Request request, HttpHeaders headers) {
        // exchange code for token //
        GoogleTokenResponse googleTokenResponse = this.oauth2InfraProvider.getGoogleToken(request.getCode());

        // get user //
        GoogleUserResponse googleUserResponse = this.oauth2InfraProvider.getGoogleUser(googleTokenResponse.getAccessToken());
        var userOpt = authQueryService.findUserByIdentifier(googleUserResponse.getEmail());

        // signup or login //
        AuthResponse authResponse;
        if (userOpt.isEmpty()) {
            authResponse = this.signup(googleUserResponse, headers);
        } else {
            authResponse = this.licenseToken(userOpt.get(), headers);
        }

        return authResponse;
    }

    private AuthResponse signup(GoogleUserResponse request, HttpHeaders headers) {
        User user = User.createSignupUser(
                User.builder()
                        .email(request.getEmail())
                        .name(request.getName())
                        .gender(request.getGender())
                        .build(),
                null
        );
        user = this.userDomainRepository.save(user);

        var authResponse = this.licenseToken(user, headers);

        return authResponse;
    }

    @Override
    public AuthResponse refresh(Optional<String> refreshToken, HttpHeaders headers) {
        // 1. Check null or empty
        if (refreshToken.isEmpty() || refreshToken.get().isBlank()) {
            throw new AppException(ErrorCode.AUTH__REFRESH_TOKEN_INVALID);
        }

        // 2. Verify & extract user ID
        String userId = this.securityInfraProvider.verifyToken(refreshToken.get(), JwtType.REFRESH_TOKEN)
                .orElseThrow(() -> new AppException(ErrorCode.AUTH__REFRESH_TOKEN_INVALID));

        // 3. Check if refresh token matches DB (to prevent reuse)
        if (!this.tokenAppService.isValidRefreshToken(refreshToken.get(), userId)) {
            throw new AppException(ErrorCode.AUTH__REFRESH_TOKEN_INVALID);
        }

        // 4. Get user
        User user = this.userQueryService.getUserById(userId);

        // 5. License new tokens (also update DB & set cookie)
        return this.licenseToken(user, headers);
    }
}

