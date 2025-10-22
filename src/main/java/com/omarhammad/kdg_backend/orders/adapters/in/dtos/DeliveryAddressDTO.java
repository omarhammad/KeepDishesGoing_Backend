package com.omarhammad.kdg_backend.orders.adapters.in.dtos;

public record DeliveryAddressDTO(String street,
                                 int number,
                                 String postalCode,
                                 String city,
                                 String country) {
}
