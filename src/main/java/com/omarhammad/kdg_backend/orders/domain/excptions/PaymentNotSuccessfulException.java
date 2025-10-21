package com.omarhammad.kdg_backend.orders.domain.excptions;

public class PaymentNotSuccessfulException extends RuntimeException {
    public PaymentNotSuccessfulException(String message) {
        super(message);
    }
}
