package dmon.SSHOP_springboot_backend.config;

import dmon.SSHOP_springboot_backend.entity.Account;
import dmon.SSHOP_springboot_backend.repository.IAccountRepository;
import dmon.SSHOP_springboot_backend.util.enumerate.RoleEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AppInitConfig {
    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(IAccountRepository accountRepo) {
        return args -> {
            if (accountRepo.findByUsername("admin").isEmpty()) {
                HashSet<String> roles = new HashSet<>();
                roles.add(RoleEnum.ADMIN.name());

                Account account = Account.builder()
                        .username("admin")
                        .email("admin@sshop.com")
                        .password(passwordEncoder.encode("123456"))
                        .roles(roles)
                        .build();

                accountRepo.save(account);
                log.warn("An admin account have been created with password 123456.Please change it !");
            }
        };
    }
}
