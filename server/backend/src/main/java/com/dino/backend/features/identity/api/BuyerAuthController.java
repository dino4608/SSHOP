package com.dino.backend.features.identity.api;

import com.dino.backend.features.identity.application.IAuthAppService;
import com.dino.backend.features.identity.application.model.*;
import com.dino.backend.infrastructure.web.annotation.AuthUser;
import com.dino.backend.infrastructure.web.model.CurrentUser;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BuyerAuthController {

    // PublicBuyerAuthController //
    @RestController
    @RequestMapping("/api/v1/public/auth")
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    public static class PublicBuyerAuthController {

        IAuthAppService authAppService;

        // QUERY //

        // lookupIdentifier //
        @GetMapping("/lookup")
        public ResponseEntity<LookupIdentifierResponse> lookupIdentifier(
                @RequestParam("email") String email) {
            return ResponseEntity.ok(this.authAppService.lookupIdentifier(email));
        }

        // COMMAND //

        // loginWithPassword //
        @PostMapping("/login/password")
        public ResponseEntity<AuthResponse> loginWithPassword(
                @Valid @RequestBody PasswordLoginRequest request) {
            HttpHeaders headers = new HttpHeaders();
            AuthResponse body = this.authAppService.login(request, headers);

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .body(body);
        }

        // signupWithPassword //
        @PostMapping("/signup/password")
        public ResponseEntity<AuthResponse> signupWithPassword(
                @Valid @RequestBody PasswordLoginRequest request) {
            HttpHeaders headers = new HttpHeaders();
            AuthResponse body = this.authAppService.signup(request, headers);

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .body(body);
        }

        // loginOrSignupWithGoogle //
        @PostMapping("/oauth2/google")
        public ResponseEntity<AuthResponse> loginOrSignupWithGoogle(
                @RequestBody GoogleOauth2Request request) {
            HttpHeaders headers = new HttpHeaders();
            AuthResponse body = this.authAppService.loginOrSignup(request, headers);

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .body(body);
        }

        // refreshToken //
        @PostMapping("/refresh")
        public ResponseEntity<AuthResponse> refresh(
                @CookieValue(name = "REFRESH_TOKEN", required = false) String refreshToken) {
            HttpHeaders headers = new HttpHeaders();
            AuthResponse authResponse = authAppService.refresh(refreshToken, headers);

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .body(authResponse);
        }

    }

    // PrivateBuyerAuthController //
    @RestController
    @RequestMapping("/api/v1/auth")
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    public static class PrivateBuyerAuthController {

        IAuthAppService authAppService;

        // QUERY //

        // getCurrentUser //
        @GetMapping("/me")
        public ResponseEntity<CurrentUserResponse> getCurrentUser(@AuthUser CurrentUser currentUser) {
            return ResponseEntity.ok(this.authAppService.getCurrentUser(currentUser));
        }

        // COMMAND //

        @PostMapping("/logout")
        public ResponseEntity<AuthResponse> logout(
                @CookieValue(name = "REFRESH_TOKEN", required = false) String refreshToken) {
            HttpHeaders headers = new HttpHeaders();
            AuthResponse authResponse = this.authAppService.logout(refreshToken, headers);

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .body(authResponse);
        }

    }
}
