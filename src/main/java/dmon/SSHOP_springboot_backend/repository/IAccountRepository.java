package dmon.SSHOP_springboot_backend.repository;

import dmon.SSHOP_springboot_backend.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAccountRepository extends JpaRepository<Account, String> {
    boolean existsByEmail(String s);
    boolean existsByPhone(String s);
    boolean existsByUsername(String s);
    Optional<Account> findByUsername(String s);
}
