package com.dino.backend.shared.api.model;

import java.util.Set;

import lombok.NonNull;

public record CurrentUser(@NonNull Long id, Set<String> roles) {
}