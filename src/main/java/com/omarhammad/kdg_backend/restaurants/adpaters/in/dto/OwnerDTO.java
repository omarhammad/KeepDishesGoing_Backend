package com.omarhammad.kdg_backend.restaurants.adpaters.in.dto;

import com.omarhammad.kdg_backend.common.sharedDomain.Email;

public record OwnerDTO(String id,
                       String firstName,
                       String lastName,
                       Email email,
                       String phoneNumber) {
}
