package dmon.SSHOP_springboot_backend.service;

import dmon.SSHOP_springboot_backend.dto.request.UserCreateRequest;
import dmon.SSHOP_springboot_backend.dto.response.UserResponse;
import dmon.SSHOP_springboot_backend.entity.User;
import dmon.SSHOP_springboot_backend.mapper.IUserMapper;
import dmon.SSHOP_springboot_backend.repository.IAccountRepository;
import dmon.SSHOP_springboot_backend.repository.IUserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    IUserRepository userRepo;
    IAccountRepository accountRepo;
    IUserMapper userMapper;

    public UserResponse createOne(UserCreateRequest body) {
        //todo: refer to account
        User user = this.userRepo.save(
                this.userMapper.toEntity(body));
        return this.userMapper.toResponse(user);
    }
}
