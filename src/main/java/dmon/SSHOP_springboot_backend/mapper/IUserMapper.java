package dmon.SSHOP_springboot_backend.mapper;

import dmon.SSHOP_springboot_backend.dto.request.UserCreateRequest;
import dmon.SSHOP_springboot_backend.dto.response.UserResponse;
import dmon.SSHOP_springboot_backend.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IUserMapper {
    User toEntity(UserCreateRequest request);
    UserResponse toResponse(User model);
}
