package com.omarhammad.kdg_backend.restaurants.adapters.in.dto;

public record AddressDTO(String street,
                         int number,
                         String postalCode,
                         String city,
                         String country) {
}
