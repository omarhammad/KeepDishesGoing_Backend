package com.omarhammad.kdg_backend.orders.domain;

public record PaymentResult(boolean successState, String message) {

    public static PaymentResult success() {
        return new PaymentResult(true, "Payment approved");

    }

    public static PaymentResult failure(String reason) {
        return new PaymentResult(false, reason);
    }


}
