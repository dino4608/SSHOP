package com.dino.backend.infrastructure.web.model;

import java.util.Set;

import lombok.NonNull;

public record CurrentUser(@NonNull Long id, Set<String> roles) {
}