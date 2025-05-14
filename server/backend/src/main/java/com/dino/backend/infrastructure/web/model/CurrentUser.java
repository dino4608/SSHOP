package com.dino.backend.infrastructure.web.model;

import lombok.Builder;

import java.util.Set;

@Builder
public record CurrentUser(String id, Set<String> roles) {
}