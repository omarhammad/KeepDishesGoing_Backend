package com.omarhammad.kdg_backend.restaurants.adapters.in.dto;

public record JwtDTO(
        String accessToken,
        long expiresIn,
        String userId,
        String username,
        String role
) {
}
