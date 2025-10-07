package com.omarhammad.kdg_backend.restaurants.ports.in;

import org.springframework.security.oauth2.jwt.Jwt;

public interface LoginUseCase {

    Jwt login(LoginCmd cmd);
}
