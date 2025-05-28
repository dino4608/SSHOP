package com.dino.backend.features.identity.application;

import com.dino.backend.features.identity.domain.User;
import com.dino.backend.shared.api.model.CurrentUser;

public interface IUserService {

    User getById(Long userId);

    User get(CurrentUser currentUser);

}
