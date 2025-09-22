package com.omarhammad.kdg_backend.common.sharedDomain;

public record Address(
        String street,
        int number,
        String postalCode,
        String city,
        String country
) {
}
