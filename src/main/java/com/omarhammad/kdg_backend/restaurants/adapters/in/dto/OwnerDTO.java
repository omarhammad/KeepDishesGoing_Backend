package com.omarhammad.kdg_backend.restaurants.adapters.in.dto;

import com.omarhammad.kdg_backend.restaurants.domain.Email;

public record OwnerDTO(String id,
                       String firstName,
                       String lastName,
                       Email email,
                       String phoneNumber) {
}
