package com.omarhammad.kdg_backend.restaurants.domain.exceptions;

public class InvalidUUIDFormatException extends RuntimeException {
    public InvalidUUIDFormatException(String message) {
        super(message);
    }
}
