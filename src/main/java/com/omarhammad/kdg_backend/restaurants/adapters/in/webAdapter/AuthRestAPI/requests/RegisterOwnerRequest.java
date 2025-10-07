package com.omarhammad.kdg_backend.restaurants.adapters.in.webAdapter.AuthRestAPI.requests;

public record RegisterOwnerRequest(String firstName,
                                   String lastName,
                                   String email,
                                   String phoneNumber,
                                   String username,
                                   String password) {
}
