package com.dino.backend.features.identity.application.service;

import org.springframework.http.HttpHeaders;

import com.dino.backend.features.identity.application.model.AuthResponse;
import com.dino.backend.features.identity.application.model.CurrentUserResponse;
import com.dino.backend.features.identity.application.model.GoogleOauth2Request;
import com.dino.backend.features.identity.application.model.LookupIdentifierResponse;
import com.dino.backend.features.identity.application.model.PasswordLoginRequest;
import com.dino.backend.shared.api.model.CurrentUser;

public interface IAuthService {

    // QUERY //

    LookupIdentifierResponse lookupIdentifier(String email);

    CurrentUserResponse getCurrentUser(CurrentUser currentUser);

    // COMMAND //

    AuthResponse login(PasswordLoginRequest request, HttpHeaders headers);

    AuthResponse signup(PasswordLoginRequest request, HttpHeaders headers);

    AuthResponse loginOrSignup(GoogleOauth2Request request, HttpHeaders headers);

    AuthResponse refresh(String refreshToken, HttpHeaders headers);

    AuthResponse logout(String refreshToken, HttpHeaders headers);

}
