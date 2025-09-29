package com.omarhammad.kdg_backend.common.sharedDomain.exceptions;

public class InvalidUUIDFormatException extends RuntimeException {
    public InvalidUUIDFormatException(String message) {
        super(message);
    }
}
