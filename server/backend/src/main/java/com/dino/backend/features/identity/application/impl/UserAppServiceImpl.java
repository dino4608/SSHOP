package com.dino.backend.features.identity.application.impl;

import com.dino.backend.features.identity.application.IUserAppService;
import com.dino.backend.features.identity.domain.User;
import com.dino.backend.features.identity.domain.repository.IUserDomainRepository;
import com.dino.backend.infrastructure.aop.AppException;
import com.dino.backend.infrastructure.aop.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserAppServiceImpl implements IUserAppService {

    IUserDomainRepository userDomainRepository;

    @Override
    public User getUserById(String userId) {
        return this.userDomainRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER__FIND_FAILED));
    }
}
