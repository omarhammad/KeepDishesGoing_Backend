package com.omarhammad.kdg_backend.orders.domain;

public record DeliveryAddress(
        String street,
        int number,
        String postalCode,
        String city,
        String country) {
}
