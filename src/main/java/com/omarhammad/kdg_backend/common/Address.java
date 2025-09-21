package com.omarhammad.kdg_backend.common;

public record Address(
        String street,
        int number,
        String postalCode,
        String city,
        String country
) {
}
