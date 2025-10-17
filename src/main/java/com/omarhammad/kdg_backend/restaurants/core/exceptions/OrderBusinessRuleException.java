package com.omarhammad.kdg_backend.restaurants.core.exceptions;

public class OrderBusinessRuleException extends RuntimeException {
    public OrderBusinessRuleException(String message) {
        super(message);
    }
}
