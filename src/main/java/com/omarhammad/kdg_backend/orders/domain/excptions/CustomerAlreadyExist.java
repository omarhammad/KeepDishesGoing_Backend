package com.omarhammad.kdg_backend.orders.domain.excptions;

public class CustomerAlreadyExist extends RuntimeException {
    public CustomerAlreadyExist(String s) {
        super(s);
    }
}
