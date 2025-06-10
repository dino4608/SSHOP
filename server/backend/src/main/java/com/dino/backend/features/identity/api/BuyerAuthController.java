package com.dino.backend.features.identity.api;

import com.dino.backend.features.identity.application.service.IAuthService;
import com.dino.backend.features.identity.application.model.*;
import com.dino.backend.shared.api.annotation.AuthUser;
import com.dino.backend.shared.api.model.CurrentUser;

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

        IAuthService authService;

        // QUERY //

        // lookupIdentifier //
        @GetMapping("/lookup")
        public ResponseEntity<LookupIdentifierResponse> lookupIdentifier(
                @RequestParam("email") String email) {
            return ResponseEntity.ok(this.authService.lookupIdentifier(email));
        }

        // COMMAND //

        // loginWithPassword //
        @PostMapping("/login/password")
        public ResponseEntity<AuthResponse> loginWithPassword(
                @Valid @RequestBody PasswordLoginRequest request) {
            HttpHeaders headers = new HttpHeaders();
            AuthResponse body = this.authService.login(request, headers);

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
            AuthResponse body = this.authService.signup(request, headers);

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
            AuthResponse body = this.authService.loginOrSignup(request, headers);

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
            AuthResponse authResponse = authService.refresh(refreshToken, headers);

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

        IAuthService authService;

        // QUERY //

        // getCurrentUser //
        @GetMapping("/me")
        public ResponseEntity<CurrentUserResponse> getCurrentUser(@AuthUser CurrentUser currentUser) {
            return ResponseEntity.ok(this.authService.getCurrentUser(currentUser));
        }

        // COMMAND //

        @PostMapping("/logout")
        public ResponseEntity<AuthResponse> logout(
                @CookieValue(name = "REFRESH_TOKEN", required = false) String refreshToken) {
            HttpHeaders headers = new HttpHeaders();
            AuthResponse authResponse = this.authService.logout(refreshToken, headers);

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .body(authResponse);
        }

    }
}
