package com.dino.backend.infrastructure.security.impl;

import com.dino.backend.features.identity.domain.User;
import com.dino.backend.infrastructure.aop.AppException;
import com.dino.backend.infrastructure.aop.ErrorCode;
import com.dino.backend.infrastructure.security.ISecurityInfraProvider;
import com.dino.backend.infrastructure.security.model.JwtType;
import com.dino.backend.infrastructure.common.Env;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.crypto.spec.SecretKeySpec;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor //create a constructor for final and @NonNull fields
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class SecurityInfraProviderImpl implements ISecurityInfraProvider {

    Env env;

    PasswordEncoder passwordEncoder;

    /**
     * // buildScope //
     * @des It means to build roles. Scope is a claim of jwt payload
     * @param roles: Set<String>
     * @return scope: String. Example "ADMIN_SELLER_USER"
     */
    private String buildScope(Set<String> roles) {
        StringJoiner stringJoiner = new StringJoiner(" ");

        if (!CollectionUtils.isEmpty(roles))
            roles.forEach(stringJoiner::add);

        return stringJoiner.toString();
    }

    // hashPassword //
    @Override
    public String hashPassword(String plain) {
        return this.passwordEncoder.encode(plain);
    }

    // matchPassword //
    @Override
    public boolean matchPassword(String plain, String hash) {
        return this.passwordEncoder.matches(plain, hash);
    }

    // getSecretKey //
    @Override
    public byte[] getSecretKey(JwtType jwtType) {
        return switch (jwtType) {
            case REFRESH_TOKEN -> this.env.REFRESH_SECRET_KEY.getBytes();
            case ACCESS_TOKEN -> this.env.ACCESS_SECRET_KEY.getBytes();
        };
    }

    // getTimeToLive //
    @Override
    public Duration getTtl(JwtType jwtType) {
        return switch (jwtType) {
            case REFRESH_TOKEN -> Duration.ofDays(this.env.REFRESH_TTL_DAYS);
            case ACCESS_TOKEN -> Duration.ofMinutes(this.env.ACCESS_TTL_MIN);
        };
    }

    // getExpiry //
    @Override
    public Instant getExpiry(JwtType jwtType) {
        return switch (jwtType) {
            case REFRESH_TOKEN -> Instant.now().plus(this.env.REFRESH_TTL_DAYS, ChronoUnit.DAYS);
            case ACCESS_TOKEN -> Instant.now().plus(this.env.ACCESS_TTL_MIN, ChronoUnit.MINUTES);
        };
    }

    // genToken //
    // NOTE: sign a JWT
    // - header json, payload json => base 64 url => header string, payload string
    // - header string, payload string, secret string => HMAC + header.algorithm.HS512 => signature bytes
    // - signature bytes => base 64 url => signature string
    // - header string, payload string, signature string => "." => token
    // NOTE:
    // - HMAC: Hash-based Message Authentication Code
    // - 1 byte = 8 bit. 1 bit = 1 binary
    // - serialize:  chuyển đổi dữ liệu thành định dạng có thể lưu trữ hoặc gửi đi
    @Override
    public String genToken(User account, JwtType jwtType) {
        // 1. create a header: algorithm HS512 + type JWT
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        // 2. create a payload: set of claims, must have the subject
        Payload payload = new Payload(
                new JWTClaimsSet.Builder()
                        .subject(account.getId())
                        .issuer("deal.dino.com")
                        .issueTime(new Date())
                        .expirationTime(new Date(this.getExpiry(jwtType).toEpochMilli()))
                        .claim("scope", this.buildScope(account.getRoles()))
                        .build()
                        .toJSONObject()
        );

        // 3. sign a jwt: HMAC + header + payload + secret
        try {
            JWSObject jwsObject = new JWSObject(header, payload);
            jwsObject.sign(new MACSigner(this.getSecretKey(jwtType)));
            return jwsObject.serialize();

        } catch (JOSEException e) {
            log.error(">>> INTERNAL: genToken 1: {}", e.getMessage());
            throw new AppException(ErrorCode.SECURITY__GEN_TOKEN_FAILED);
        }
    }

    // verifyToken //
    // NOTE:
    // - should log errors and return optional or boolean => clean code, because of less try-catch in the services
    // - should only throw internal system errors => safe code
    // - for the services will process logic => single responsibility
    @Override
    public Optional<String> verifyToken(String token, JwtType jwtType) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(
                this.getSecretKey(jwtType),
                JWSAlgorithm.HS512.getName());

        JwtDecoder jwtDecoder = NimbusJwtDecoder
                .withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();

        try {
            Jwt jwt = jwtDecoder.decode(token);

            String subject = jwt.getSubject();

            return Optional.of(subject);

        } catch (JwtException e) {
            log.warn(">>> WARNING: verifyToken: {}", e.getMessage());
            return Optional.empty();
        }
    }
}
