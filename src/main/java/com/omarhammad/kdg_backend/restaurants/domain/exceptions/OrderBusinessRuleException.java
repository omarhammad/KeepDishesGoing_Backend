package com.omarhammad.kdg_backend.restaurants.domain.exceptions;

public class OrderBusinessRuleException extends RuntimeException {
    public OrderBusinessRuleException(String message) {
        super(message);
    }
}
