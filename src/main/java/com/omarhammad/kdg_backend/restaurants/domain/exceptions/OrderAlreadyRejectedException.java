package com.omarhammad.kdg_backend.restaurants.domain.exceptions;

public class OrderAlreadyRejectedException extends RuntimeException {
    public OrderAlreadyRejectedException(String s) {
        super(s);
    }
}
