package com.omarhammad.kdg_backend.restaurants.core;

import com.omarhammad.kdg_backend.restaurants.domain.Email;
import com.omarhammad.kdg_backend.restaurants.domain.Owner;
import com.omarhammad.kdg_backend.restaurants.ports.in.RegisterNewOwnerUseCase;
import com.omarhammad.kdg_backend.restaurants.ports.in.RegisterOwnerCmd;
import com.omarhammad.kdg_backend.restaurants.ports.out.AuthProviderPort;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DefaultRegisterNewOwnerUseCase implements RegisterNewOwnerUseCase {

    private AuthProviderPort authProviderPort;

    @Override
    public Jwt register(RegisterOwnerCmd cmd) {

        Owner owner = new Owner(
                cmd.firstName(),
                cmd.lastName(),
                new Email(cmd.email()),
                cmd.phoneNumber(),
                cmd.username(),
                cmd.password()
        );
        return authProviderPort.register(owner);
    }

}
