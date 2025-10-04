package com.omarhammad.kdg_backend.restaurants.domain.exceptions;

public class DishAlreadyPublishedException extends RuntimeException {
    public DishAlreadyPublishedException(String message) {
        super(message);
    }
}
