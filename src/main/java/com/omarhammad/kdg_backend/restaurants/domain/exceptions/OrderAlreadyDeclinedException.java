package com.omarhammad.kdg_backend.restaurants.domain.exceptions;

public class OrderAlreadyDeclinedException extends RuntimeException {
    public OrderAlreadyDeclinedException(String s) {
        super(s);
    }
}
