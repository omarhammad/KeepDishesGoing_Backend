package com.omarhammad.kdg_backend.orders.domain.excptions;

public class InvalidUUIDFormatException extends RuntimeException {
    public InvalidUUIDFormatException(String message) {
        super(message);
    }
}
