package com.dino.backend.features.identity.application;

import com.dino.backend.features.identity.application.model.*;
import com.dino.backend.features.identity.domain.User;
import com.dino.backend.infrastructure.web.model.CurrentUser;
import org.springframework.http.HttpHeaders;

import java.util.Optional;

public interface IAuthAppService {

    // QUERY //

    LookupIdentifierResponse lookupIdentifier(String email);

    CurrentUserResponse getCurrentUser(CurrentUser currentUser);

    // COMMAND //

    AuthResponse login(PasswordLoginRequest request, HttpHeaders headers);

    AuthResponse signup(PasswordLoginRequest request, HttpHeaders headers);

    AuthResponse loginOrSignup(GoogleOauth2Request request, HttpHeaders headers);

    AuthResponse refresh(String refreshToken, HttpHeaders headers);

    void logout(String refreshToken, HttpHeaders headers);

}
