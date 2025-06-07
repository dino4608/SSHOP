package com.dino.backend.features.ordering.application.model;

public record ConfirmCheckoutRes(Boolean isConfirmed, int count) {

    public static ConfirmCheckoutRes success(int count) {
        return new ConfirmCheckoutRes(true, count);
    }
}
