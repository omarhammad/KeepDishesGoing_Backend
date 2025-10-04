package com.omarhammad.kdg_backend.restaurants.domain.exceptions;

public class OwnerAlreadyHaveRestaurantException extends RuntimeException {
    public OwnerAlreadyHaveRestaurantException(String message) {
        super(message);
    }
}
