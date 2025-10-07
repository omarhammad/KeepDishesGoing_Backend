package com.omarhammad.kdg_backend.restaurants.core;

import com.omarhammad.kdg_backend.restaurants.ports.in.LoginCmd;
import com.omarhammad.kdg_backend.restaurants.ports.in.LoginUseCase;
import com.omarhammad.kdg_backend.restaurants.ports.out.AuthProviderPort;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DefaultLoginUseCase implements LoginUseCase {

    private AuthProviderPort authProviderPort;

    @Override
    public Jwt login(LoginCmd cmd) {

        return authProviderPort.login(cmd.username(), cmd.password());
    }

}
