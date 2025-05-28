package com.dino.backend.shared.application.utils;

public record Deleted(boolean isDeleted, int count) {

    public static Deleted success() {
        return new Deleted(true, 1);
    }

    public static Deleted success(int count) {
        return new Deleted(true, count);
    }

    public static Deleted failure() {
        return new Deleted(false, 1);
    }

    // NOTE: count or total
    // ✅ count: số lượng trong 1 tập con, hành động, kết quả tạm thời
    // ✅ total: toàn bộ số lượng hiện tại, đặc biệt là trạng thái toàn cục
}
