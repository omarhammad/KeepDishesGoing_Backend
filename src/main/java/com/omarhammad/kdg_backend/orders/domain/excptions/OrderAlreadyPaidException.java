package com.omarhammad.kdg_backend.orders.domain.excptions;

public class OrderAlreadyPaidException extends RuntimeException {
    public OrderAlreadyPaidException(String message) {
        super(message);
    }
}
