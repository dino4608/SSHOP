package com.dino.backend.features.identity.application.impl;

import com.dino.backend.features.identity.application.IUserQueryService;
import com.dino.backend.features.identity.domain.User;
import com.dino.backend.features.identity.domain.repository.IUserDomainRepository;
import com.dino.backend.infrastructure.aop.AppException;
import com.dino.backend.infrastructure.aop.ErrorCode;

public class UserQueryServiceImpl implements IUserQueryService {

    IUserDomainRepository userDomainRepository;

    @Override
    public User getUserById(String userId) {
        return this.userDomainRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER__FIND_FAILED));
    }
}
