package com.dino.backend.shared.utils;

import lombok.Getter;

import java.util.Collection;

@Getter
public class Required<T> {

    private final T value;

    // Private constructor to ensure that an instance can only be created through static methods.
    private Required(T value) {
        this.value = value;
    }

    // Static method to check if a value is not null.
    // If the value is null, it throws a NullPointerException.
    // Returns the value of type T if it is not null.
    public static <T> T notNull(T value) {
        if (value == null) {
            throw new NullPointerException("Value must not be null");
        }
        return value;
    }

    // Static method to check if a Collection is not null and not empty.
    // If the collection is null or empty, it throws an IllegalArgumentException.
    // Returns the collection itself if it is neither null nor empty.
    public static <T> Collection<T> notEmpty(Collection<T> value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Collection must not be null or empty");
        }
        return value;
    }

    // Static method to check if a String is not null and not empty (not blank).
    // If the string is null or blank, it throws an IllegalArgumentException.
    // Returns the string itself if it is neither null nor blank.
    public static String notBlank(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("String must not be null or blank");
        }
        return value;
    }

    // Getter method to retrieve the value (if needed).
//    public T getValue() {
//        return value;
//    }
}
