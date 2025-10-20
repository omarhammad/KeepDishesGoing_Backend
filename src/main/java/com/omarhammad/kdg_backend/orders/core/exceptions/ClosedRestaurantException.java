package com.omarhammad.kdg_backend.orders.core.exceptions;

public class ClosedRestaurantException extends RuntimeException {
    public ClosedRestaurantException(String message) {
        super(message);
    }
}
