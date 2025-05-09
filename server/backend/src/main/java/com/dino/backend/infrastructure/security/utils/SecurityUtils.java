package com.dino.backend.infrastructure.security.utils;

import com.dino.backend.infrastructure.aop.AppException;
import com.dino.backend.infrastructure.aop.ErrorCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Optional;

public final class SecurityUtils {
    private SecurityUtils() {}

    // PRIVATE //

    /**
     * extract the subject, a claim of jwt payload, from jwt principal
     * @param authentication: Authentication
     * @return String
     */
    private String extractPrincipal(Authentication authentication) {
        //Code: authentication.getPrincipal() instanceof Jwt jwt
        //Mean: if authentication.getPrincipal() has the Jwt type, assign it to the jwt variable
        if (authentication == null) {
            return null;
        } else if (authentication.getPrincipal() instanceof UserDetails springSecurityUser) {
            return springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof Jwt jwt) {
            return jwt.getSubject();
        } else if (authentication.getPrincipal() instanceof String string) {
            return string;
        }
        return null;
    }

    // PUBLIC //
    /**
     * Get the subject, a claim of jwt payload
     * @return the current user.
     */
    public String extractCurrentUserId() {
        // 1. get Security Context
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // 2. get the subject, a claim of jwt payload
        Optional<String> subject = Optional.ofNullable(extractPrincipal(auth));

        return subject.orElseThrow(() -> new AppException(ErrorCode.SECURITY__GET_CURRENT_USER_FAILED));
    }
}

