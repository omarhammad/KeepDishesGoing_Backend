package com.omarhammad.kdg_backend.restaurants.domain;

public record Address(
        String street,
        int number,
        String postalCode,
        String city,
        String country
) {
}
