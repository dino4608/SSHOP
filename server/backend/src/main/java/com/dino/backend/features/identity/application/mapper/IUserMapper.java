package com.dino.backend.features.identity.application.mapper;

import com.dino.backend.features.identity.application.model.PasswordLoginRequest;
import com.dino.backend.features.identity.domain.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IUserMapper {

    User toUser(PasswordLoginRequest request);


}
