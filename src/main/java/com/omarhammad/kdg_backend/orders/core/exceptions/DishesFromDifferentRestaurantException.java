package com.omarhammad.kdg_backend.orders.core.exceptions;

public class DishesFromDifferentRestaurantException extends RuntimeException {
    public DishesFromDifferentRestaurantException(String message) {
        super(message);
    }
}
