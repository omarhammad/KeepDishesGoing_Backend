package com.omarhammad.kdg_backend.orders.core.exceptions;

public class InvalidDishesException extends RuntimeException {
    public InvalidDishesException(String message) {
        super(message);
    }
}
