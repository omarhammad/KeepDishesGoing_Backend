package com.omarhammad.kdg_backend.restaurants.ports.out;

import com.omarhammad.kdg_backend.restaurants.domain.Owner;
import org.springframework.security.oauth2.jwt.Jwt;

public interface AuthProviderPort {

    Jwt login(String username,String password);

    Jwt register(Owner owner);


}
