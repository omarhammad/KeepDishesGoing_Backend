package com.omarhammad.kdg_backend.restaurants.domain.exceptions;

public class OrderAlreadyAcceptedException extends RuntimeException {
    public OrderAlreadyAcceptedException(String s) {
        super(s);
    }
}
