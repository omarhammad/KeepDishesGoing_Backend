package com.omarhammad.kdg_backend.orders.adapters.in.dtos;

import com.omarhammad.kdg_backend.orders.domain.DeliveryAddress;

public record CustomerDTO(
        String id,
        String firstName,
        String lastName,
        DeliveryAddressDTO deliveryAddress,
        String email,
        String phoneNumber) {
}
