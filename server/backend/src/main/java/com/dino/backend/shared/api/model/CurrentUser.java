package com.dino.backend.shared.api.model;

import java.util.Set;

import com.dino.backend.features.identity.domain.User;
import lombok.NonNull;

public record CurrentUser(@NonNull Long id, Set<String> roles) {

    public User toUser() {
        return User.builder().id(this.id).build();
    }
}