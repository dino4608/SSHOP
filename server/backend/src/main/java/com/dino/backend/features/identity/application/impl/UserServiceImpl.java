package com.dino.backend.features.identity.application.impl;

import com.dino.backend.features.identity.application.IUserService;
import com.dino.backend.features.identity.domain.User;
import com.dino.backend.features.identity.domain.repository.IUserRepository;
import com.dino.backend.shared.api.model.CurrentUser;
import com.dino.backend.shared.domain.exception.AppException;
import com.dino.backend.shared.domain.exception.ErrorCode;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserServiceImpl implements IUserService {

    IUserRepository userRepository;

    @Override
    public User getById(Long userId) {
        return this.userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER__FIND_FAILED));
    }

    @Override
    public User get(CurrentUser currentUser) {
        return this.getById(currentUser.id());
    }
}
