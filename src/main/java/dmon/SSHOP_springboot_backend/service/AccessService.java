package dmon.SSHOP_springboot_backend.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import dmon.SSHOP_springboot_backend.dto.request.AccessRequest;
import dmon.SSHOP_springboot_backend.dto.response.AccessResponse;
import dmon.SSHOP_springboot_backend.entity.Account;
import dmon.SSHOP_springboot_backend.exception.AppException;
import dmon.SSHOP_springboot_backend.exception.ExceptionCode;
import dmon.SSHOP_springboot_backend.repository.IAccountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Set;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AccessService {
    IAccountRepository accountRepo;

    @NonFinal
    @Value("${jwt.secretKey}")
    protected  String SECRET_KEY;

    public AccessResponse  authenticate(AccessRequest request) {
        Account account = this.accountRepo.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ExceptionCode.OBJECT_NOT_FOUND, "account"));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated =  passwordEncoder.matches(request.getPassword(), account.getPassword());

        if (!authenticated)
            throw new AppException(ExceptionCode.UNAUTHENTICATED);
        var token = generateToken(account);

        return AccessResponse
                .builder()
                .authenticated(authenticated)
                .token(token)
                .build();
    }

    private String generateToken(Account account) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(account.getAccountId())
                .issuer("sshop.dmon.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(400, ChronoUnit.DAYS).toEpochMilli()
                ))
                .claim("scope", buildScope(account.getRoles()))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SECRET_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }

    private String buildScope(Set<String> roles){
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(roles))
            roles.forEach(stringJoiner::add);

        return stringJoiner.toString();
    }
}
