package dmon.SSHOP_springboot_backend.repository;

import dmon.SSHOP_springboot_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, String> {

}
