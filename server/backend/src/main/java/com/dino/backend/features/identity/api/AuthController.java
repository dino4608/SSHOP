package com.dino.backend.features.identity.api;

import com.dino.backend.features.identity.application.IAuthAppService;
import com.dino.backend.features.identity.application.model.*;
import com.dino.backend.features.identity.application.IAuthQueryService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    // BUYER //
    @RestController
    @RequestMapping("/api/v1/auth")
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    public static class AuthBuyerController {
        // QUERY //
        IAuthQueryService authQueryService;

        // lookupIdentifier //
        @GetMapping("/lookup")
        public ResponseEntity<LookupIdentifierResponse> lookupIdentifier(
                @RequestParam("email") String email
        ) {
            return ResponseEntity.ok(authQueryService.lookupIdentifier(email));
        }

        // COMMAND //
        IAuthAppService authAppService;

        // loginWithPassword //
        @PostMapping("/login/password")
        public ResponseEntity<AuthResponse> loginWithPassword(
                @Valid  @RequestBody PasswordLoginRequest request
        ) {
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
                @Valid @RequestBody PasswordLoginRequest request
        ) {
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
                @RequestBody GoogleOauth2Request request
        ) {
            HttpHeaders headers = new HttpHeaders();
            AuthResponse body = this.authAppService.loginOrSignup(request, headers);

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .body(body);
        }
    }
}
