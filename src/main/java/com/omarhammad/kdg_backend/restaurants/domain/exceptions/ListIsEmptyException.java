package com.omarhammad.kdg_backend.restaurants.domain.exceptions;

public class ListIsEmptyException extends RuntimeException {
    public ListIsEmptyException(String s) {
        super(s);
    }
}
