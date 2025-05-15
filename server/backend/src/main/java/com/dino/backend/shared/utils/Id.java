package com.dino.backend.shared.utils;

import java.util.Optional;
import java.util.UUID;

public class Id {

    private final UUID value;

    private Id(UUID value) {
        this.value = value;
    }

    public String get() {
        return value.toString();
    }

    public static Id random() {
        return new Id(UUID.randomUUID());
    }

    public String toString() {
        return value.toString();
    }

    public static boolean isValid(String input) {
        try {
            UUID.fromString(input);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    // Factory method to create Id from string and return Optional
    public static Optional<Id> from(String id) {
        try {
            UUID uuid = UUID.fromString(id);
            return Optional.of(new Id(uuid));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}