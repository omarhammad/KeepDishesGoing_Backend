package com.omarhammad.kdg_backend.orders.adapters.in.webAdapters.requests;

public record DeliveryAddressRequest(String street,
                                     int number,
                                     String postalCode,
                                     String city,
                                     String country) {
}
