package com.omarhammad.kdg_backend.restaurants.domain.exceptions;

public class ScheduleTooSoonException extends RuntimeException {
    public ScheduleTooSoonException(String s) {
        super(s);
    }
}
