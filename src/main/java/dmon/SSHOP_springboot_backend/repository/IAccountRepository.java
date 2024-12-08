package dmon.SSHOP_springboot_backend.repository;

import dmon.SSHOP_springboot_backend.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountRepository extends JpaRepository<Account, String> {
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
}
