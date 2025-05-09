package com.dino.backend.features.identity.application;

import com.dino.backend.features.identity.domain.User;

public interface IUserQueryService {

    User getUserById(String userId);

}
