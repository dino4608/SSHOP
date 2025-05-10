package com.dino.backend.features.identity.application;

import com.dino.backend.features.identity.application.model.GoogleOauth2Request;
import com.dino.backend.features.identity.application.model.AuthResponse;
import com.dino.backend.features.identity.application.model.LookupIdentifierResponse;
import com.dino.backend.features.identity.application.model.PasswordLoginRequest;
import com.dino.backend.features.identity.domain.User;
import org.springframework.http.HttpHeaders;

import java.util.Optional;

public interface IAuthAppService {

    LookupIdentifierResponse lookupIdentifier(String email);

    AuthResponse login(PasswordLoginRequest request, HttpHeaders headers);

    AuthResponse signup(PasswordLoginRequest request, HttpHeaders headers);

    AuthResponse loginOrSignup(GoogleOauth2Request request, HttpHeaders headers);

    AuthResponse refresh(Optional<String> refreshToken, HttpHeaders headers);

}
