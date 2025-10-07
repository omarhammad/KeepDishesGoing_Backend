package com.omarhammad.kdg_backend.restaurants.adapters.in.webAdapter.AuthRestAPI;

import com.omarhammad.kdg_backend.restaurants.adapters.in.dto.JwtDTO;
import com.omarhammad.kdg_backend.restaurants.adapters.in.webAdapter.AuthRestAPI.requests.RegisterOwnerRequest;
import com.omarhammad.kdg_backend.restaurants.ports.in.RegisterOwnerCmd;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class AuthMapper {


    public JwtDTO toJwtDTO(Jwt jwt) {
        Map<String, Object> claims = jwt.getClaims();
        List<String> roles = new ArrayList<>();
        Map<String, Object> realmAccess = (Map<String, Object>) claims.get("realm_access");
        if (realmAccess != null && realmAccess.get("roles") != null) {
            roles.addAll((List<String>) realmAccess.get("roles"));
        }

        assert jwt.getExpiresAt() != null;
        return new JwtDTO(
                jwt.getTokenValue(),
                "%d mins".formatted((jwt.getExpiresAt().getEpochSecond() - Instant.now().getEpochSecond()) / 60),
                jwt.getSubject(),
                (String) claims.get("preferred_username"),
                roles.toString()
        );
    }

    public RegisterOwnerCmd toRegisterOwnerCmd(RegisterOwnerRequest request) {
        return new RegisterOwnerCmd(
                request.firstName(),
                request.lastName(),
                request.email(),
                request.phoneNumber(),
                request.username(),
                request.password()
        );
    }

}
