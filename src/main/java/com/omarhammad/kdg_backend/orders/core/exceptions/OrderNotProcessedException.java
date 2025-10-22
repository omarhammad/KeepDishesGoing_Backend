package com.omarhammad.kdg_backend.orders.core.exceptions;

public class OrderNotProcessedException extends RuntimeException {
    public OrderNotProcessedException(String message) {
        super(message);
    }
}
