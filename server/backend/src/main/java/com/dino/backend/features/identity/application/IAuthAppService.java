package com.dino.backend.features.identity.application;

import com.dino.backend.features.identity.application.model.*;
import com.dino.backend.features.identity.domain.User;
import com.dino.backend.infrastructure.web.model.CurrentUser;
import org.springframework.http.HttpHeaders;

import java.util.Optional;

public interface IAuthAppService {

    LookupIdentifierResponse lookupIdentifier(String email);

    /** // getCurrentUser //
     * @des Retrieves the current user's information from the CurrentUser object.
     * @param currentUser: CurrentUser: The current user's information (including ID).
     * @return user: User: information with sensitive details removed.
     */
    CurrentUserResponse getCurrentUser(CurrentUser currentUser);

    AuthResponse login(PasswordLoginRequest request, HttpHeaders headers);

    AuthResponse signup(PasswordLoginRequest request, HttpHeaders headers);

    AuthResponse loginOrSignup(GoogleOauth2Request request, HttpHeaders headers);

    AuthResponse refresh(String refreshToken, HttpHeaders headers);

}
