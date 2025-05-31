package com.dino.backend.features.identity.application.impl;

import java.util.Objects;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dino.backend.features.identity.application.IAuthService;
import com.dino.backend.features.identity.application.ITokenService;
import com.dino.backend.features.identity.application.IUserService;
import com.dino.backend.features.identity.application.mapper.IUserMapper;
import com.dino.backend.features.identity.application.model.AuthResponse;
import com.dino.backend.features.identity.application.model.CurrentUserResponse;
import com.dino.backend.features.identity.application.model.GoogleOauth2Request;
import com.dino.backend.features.identity.application.model.GoogleUserResponse;
import com.dino.backend.features.identity.application.model.LookupIdentifierResponse;
import com.dino.backend.features.identity.application.model.PasswordLoginRequest;
import com.dino.backend.features.identity.application.provider.IIdentityCookieProvider;
import com.dino.backend.features.identity.application.provider.IIdentityOauth2Provider;
import com.dino.backend.features.identity.application.provider.IIdentitySecurityProvider;
import com.dino.backend.features.identity.domain.User;
import com.dino.backend.features.identity.domain.repository.IUserRepository;
import com.dino.backend.shared.api.model.CurrentUser;
import com.dino.backend.shared.application.utils.Id;
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
public class AuthServiceImpl implements IAuthService {

    ITokenService tokenService;

    IUserService userService;

    IUserRepository userRepository;

    IUserMapper userMapper;

    IIdentitySecurityProvider securityProvider;

    IIdentityOauth2Provider oauth2Provider;

    IIdentityCookieProvider cookieProvider;

    // QUERY //

    // findUserByIdentifier //
    private Optional<User> findUserByIdentifier(String email) {
        return this.userRepository.findByEmail(email);
    }

    // LookupIdentifierResponse //
    @Override
    public LookupIdentifierResponse lookupIdentifier(String email) {
        Optional<User> userOpt = this.findUserByIdentifier(email);

        return LookupIdentifierResponse.builder()
                .isEmailProvided(userOpt.isPresent())
                .isPasswordProvided(userOpt.isPresent() && userOpt.get().getPassword() != null)
                .build();
    }

    // getCurrentUser //
    @Override
    public CurrentUserResponse getCurrentUser(CurrentUser currentUser) {
        // 1. Retrieve user from database based on the current user's ID.
        User user = this.userService.getById(currentUser.id());

        // 2. Return the user object after processing sensitive information (password,
        // roles, etc.).
        return this.userMapper.toCurrentUserResponse(user);
    }

    // COMMAND //

    // authenticate //
    private AuthResponse authenticate(User user, HttpHeaders headers) {
        // get tokens
        var tokenPair = this.securityProvider.genTokenPair(user);

        // update refresh token to database
        this.tokenService.updateRefreshToken(tokenPair.refreshToken(), tokenPair.refreshTokenExpiry(), user.getId());

        // set refresh token to cookie
        this.cookieProvider.attachRefreshToken(headers, tokenPair.refreshToken(), tokenPair.refreshTokenTtl());

        return AuthResponse.builder()
                .isAuthenticated(true)
                .accessToken(tokenPair.accessToken())
                .currentUser(this.userMapper.toCurrentUserResponse(user))
                .build();
    }

    // unauthenticate //
    private AuthResponse unauthenticate(HttpHeaders headers) {
        this.cookieProvider.clearRefreshToken(headers);

        return AuthResponse.builder()
                .isAuthenticated(false)
                .build();
    }

    // login + PasswordLoginRequest //
    @Override
    public AuthResponse login(PasswordLoginRequest request, HttpHeaders headers) {
        // get user
        User user = this.findUserByIdentifier(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.AUTH__IDENTIFIER_NOT_FOUND));

        // match password
        if (!this.securityProvider.matchPassword(request.getPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.AUTH__PASSWORD_INVALID);
        }

        // license token

        return this.authenticate(user, headers);
    }

    // signup + PasswordLoginRequest //
    @Override
    public AuthResponse signup(PasswordLoginRequest request, HttpHeaders headers) {
        // don't exist user
        if (this.findUserByIdentifier(request.getEmail()).isPresent()) {
            throw new AppException(ErrorCode.AUTH__IDENTIFIER_EXISTED);
        }

        // create
        User user = User.createSignupUser(
                this.userMapper.toUser(request),
                this.securityProvider.hashPassword(request.getPassword()));
        user = this.userRepository.save(user);

        // license token

        return this.authenticate(user, headers);
    }

    // signup + GoogleUserResponse //
    private AuthResponse signup(GoogleUserResponse googleUser, HttpHeaders headers) {
        User user = User.createSignupUser(
                User.builder()
                        .email(googleUser.getEmail())
                        .name(googleUser.getName())
                        .gender(googleUser.getGender())
                        .build(),
                null);
        user = this.userRepository.save(user);

        return this.authenticate(user, headers);
    }

    // loginOrSignup + GoogleOauth2Request //
    @Override
    public AuthResponse loginOrSignup(GoogleOauth2Request request, HttpHeaders headers) {
        // 1. authViaGoogle
        var googleUserResponse = this.oauth2Provider.authViaGoogle(request.getCode());

        // get user //
        var userOpt = this.findUserByIdentifier(googleUserResponse.getEmail());

        // signup or login //
        AuthResponse authResponse;
        if (userOpt.isEmpty()) {
            authResponse = this.signup(googleUserResponse, headers);
        } else {
            authResponse = this.authenticate(userOpt.get(), headers);
        }

        return authResponse;
    }

    // refresh //
    @Override
    public AuthResponse refresh(String refreshToken, HttpHeaders headers) {
        // 1. check refreshToken is not blank
        if (!StringUtils.hasText(refreshToken))
            return this.unauthenticate(headers);

        // 2. verify & extract user ID
        Id userId = this.securityProvider.verifyRefreshToken(refreshToken).orElse(null);

        if (Objects.isNull(userId))
            return this.unauthenticate(headers);

        // 3. check if refresh token matches DB (to prevent reuse)
        if (!this.tokenService.isRefreshTokenValid(refreshToken, userId.value()))
            return this.unauthenticate(headers);

        // 4. get user
        User user = this.userService.getById(userId.value());

        // 5. authenticate successfully (license tokens, update DB & set cookie)
        return this.authenticate(user, headers);
    }

    // logout //
    @Override
    public AuthResponse logout(String refreshToken, HttpHeaders headers) {
        // 1. Check null or empty refresh token
        if (refreshToken == null || refreshToken.isBlank()) {
            return this.unauthenticate(headers);
        }

        // 2. Verify & extract user ID
        Id userId = this.securityProvider.verifyRefreshToken(refreshToken)
                .orElse(null);
        if (userId == null) {
            return this.unauthenticate(headers);
        }

        // 3. Check if refresh token matches DB (to prevent reuse)
        if (!this.tokenService.isRefreshTokenValid(refreshToken, userId.value())) {
            return this.unauthenticate(headers);
        }

        // 4. Remove refresh token from DB (or set it as null)
        this.tokenService.updateRefreshToken("", null, userId.value());

        // 5. Clear refresh token in cookies
        this.cookieProvider.clearRefreshToken(headers);

        // Return unauthenticated response
        return AuthResponse.builder().isAuthenticated(true).build();
    }
}
