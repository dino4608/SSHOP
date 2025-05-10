package com.dino.backend.features.identity.application;

import com.dino.backend.features.identity.domain.User;

public interface IUserAppService {

    User getUserById(String userId);

}
