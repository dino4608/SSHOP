package com.dino.backend.features.identity.application;

import com.dino.backend.features.identity.domain.Token;

public interface ITokenQueryService {

    Token getById(String userId);
}
