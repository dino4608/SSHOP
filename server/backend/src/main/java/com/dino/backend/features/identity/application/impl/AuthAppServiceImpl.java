package com.dino.backend.features.identity.application.impl;

import com.dino.backend.features.identity.application.IAuthAppService;
import com.dino.backend.features.identity.application.IAuthQueryService;
import com.dino.backend.features.identity.application.ITokenAppService;
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

    IUserDomainRepository userDomainRepository;

    IUserMapper userMapper;

    IAuthQueryService authQueryService;

    ITokenAppService tokenAppService;

    ISecurityInfraProvider securityInfraProvider;

    IOauth2InfraProvider oauth2InfraProvider;

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

    // licenseToken //
    private AuthResponse licenseToken(User user, HttpHeaders headers) {
        // get tokens
        String ACCESS_TOKEN = this.securityInfraProvider.genToken(user, JwtType.ACCESS_TOKEN);
        String REFRESH_TOKEN = this.securityInfraProvider.genToken(user, JwtType.REFRESH_TOKEN);
        Duration refreshDuration = this.securityInfraProvider.getValidDuration(JwtType.REFRESH_TOKEN);
        Instant refreshExpDate = this.securityInfraProvider.getExpirationDate(JwtType.REFRESH_TOKEN);

        // update refresh token to database
        this.tokenAppService.updateRefreshToken(REFRESH_TOKEN, refreshExpDate, user.getId());

        // set refresh token to cookie
        HttpCookie cookie = ResponseCookie.from("REFRESH_TOKEN", REFRESH_TOKEN)
                .httpOnly(true)
                .sameSite("None")
                .secure(true)
                .path("/")
                .maxAge(refreshDuration)
                .build();
        headers.add(HttpHeaders.SET_COOKIE, cookie.toString());

        return AuthResponse.builder()
                .isAuthenticated(true)
                .accessToken(ACCESS_TOKEN)
                .user(User.responseUser(user))
                .build();
    }
}

