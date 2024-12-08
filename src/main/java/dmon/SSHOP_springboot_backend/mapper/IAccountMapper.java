package dmon.SSHOP_springboot_backend.mapper;

import dmon.SSHOP_springboot_backend.dto.request.AccountCreateRequest;
import dmon.SSHOP_springboot_backend.dto.request.AccountUpdateRequest;
import dmon.SSHOP_springboot_backend.dto.request.UserCreateRequest;
import dmon.SSHOP_springboot_backend.dto.response.AccountResponse;
import dmon.SSHOP_springboot_backend.dto.response.UserResponse;
import dmon.SSHOP_springboot_backend.entity.Account;
import dmon.SSHOP_springboot_backend.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IAccountMapper {
    Account toEntity(AccountCreateRequest body);
    Account toEntity(AccountUpdateRequest body);
    @Mapping(target = "accountId", source = "accountId")
    AccountResponse toResponse(Account entity);
}
