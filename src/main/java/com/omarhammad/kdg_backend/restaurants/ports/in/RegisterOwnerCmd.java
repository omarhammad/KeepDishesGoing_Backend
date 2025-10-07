package com.omarhammad.kdg_backend.restaurants.ports.in;

import com.omarhammad.kdg_backend.restaurants.domain.Email;

public record RegisterOwnerCmd(String firstName,
                               String lastName,
                               String email,
                               String phoneNumber,
                               String username,
                               String password) {

}
