package com.dino.backend.shared.application.utils;

public record Deleted(boolean isDeleted, int count) {

    public static Deleted success(int count) {
        return new Deleted(true, count);
    }
    
    public static Deleted success() {
        return new Deleted(true, 1);
    }

    public static Deleted failure() {
        return new Deleted(false, 0);
    }
}
