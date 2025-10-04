package com.omarhammad.kdg_backend.restaurants.domain.exceptions;

public class DishAlreadyUnPublishedException extends RuntimeException {
    public DishAlreadyUnPublishedException(String message) {
        super(message);
    }
}
