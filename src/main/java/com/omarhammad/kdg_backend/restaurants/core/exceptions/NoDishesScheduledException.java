package com.omarhammad.kdg_backend.restaurants.core.exceptions;

public class NoDishesScheduledException extends RuntimeException {
    public NoDishesScheduledException(String s) {
        super(s);
    }
}
