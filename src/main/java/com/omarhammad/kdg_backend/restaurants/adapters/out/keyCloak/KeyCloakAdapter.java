package com.omarhammad.kdg_backend.restaurants.adapters.out.keyCloak;

import com.omarhammad.kdg_backend.restaurants.adapters.in.exceptions.InvalidCredentialsException;
import com.omarhammad.kdg_backend.restaurants.adapters.out.exceptions.KeycloakServerException;
import com.omarhammad.kdg_backend.restaurants.adapters.out.exceptions.OwnerAlreadyExistInKeycloakException;
import com.omarhammad.kdg_backend.restaurants.domain.Email;
import com.omarhammad.kdg_backend.restaurants.domain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.Owner;
import com.omarhammad.kdg_backend.restaurants.ports.out.AuthProviderPort;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadOwnerPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class KeyCloakAdapter implements AuthProviderPort, LoadOwnerPort {


    private final WebClient webClient;
    private final JwtDecoder jwtDecoder;

    @Value("${keycloak.client.secret-key}")
    private String secretKey;

    public KeyCloakAdapter() {
        this.webClient = WebClient.builder().build();
        this.jwtDecoder = NimbusJwtDecoder
                .withJwkSetUri("http://localhost:8180/realms/keepdishesgoing/protocol/openid-connect/certs")
                .build();
    }

    @Override
    public Jwt login(String username, String password) {
        try {
            Map<String, Object> response = webClient.post()
                    .uri("http://localhost:8180/realms/keepdishesgoing/protocol/openid-connect/token")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .body(BodyInserters.fromFormData("grant_type", "password")
                            .with("client_id", "backend-client")
                            .with("client_secret", secretKey)
                            .with("username", username)
                            .with("password", password))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                    })
                    .block();


            String accessToken = (String) response.get("access_token");
            return jwtDecoder.decode(accessToken);
        } catch (WebClientResponseException e) {
            throw new InvalidCredentialsException("Invalid username/password try agin");
        }

    }

    @Override
    public Jwt register(Owner owner) {
        try {
            String adminAccessToken = getAdminAccessToken();

            Map<String, Object> userPayload = Map.of(
                    "username", owner.getUsername(),
                    "firstName", owner.getFirstName(),
                    "lastName", owner.getLastName(),
                    "email", owner.getEmail().email(),
                    "enabled", true,
                    "credentials", List.of(Map.of(
                            "type", "password",
                            "value", owner.getPassword(),
                            "temporary", false
                    )),
                    "attributes", Map.of(
                            "phoneNumber", List.of(owner.getPhoneNumber())
                    )
            );


            webClient.post()
                    .uri("http://localhost:8180/admin/realms/keepdishesgoing/users")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + adminAccessToken)
                    .bodyValue(userPayload)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();


            // post role "owner"
            postUserRole(owner.getUsername(), "owner", adminAccessToken);

            return login(owner.getUsername(), owner.getPassword());
        } catch (WebClientResponseException e) {
            String message = getMessage(owner, e);
            throw new KeycloakServerException(message);
        }
    }

    @Override
    public Optional<Owner> findOwnerById(Id<Owner> ownerId) {
        try {

            Map<String, Object> user = webClient.get()
                    .uri("http://localhost:8180/admin/realms/keepdishesgoing/users/{id}", ownerId.value())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAdminAccessToken())
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                    })
                    .block();
            if (user == null || user.isEmpty()) {
                return Optional.empty();
            }
            Owner owner = toOwner(user);
            return Optional.of(owner);
        } catch (WebClientResponseException.NotFound exception) {
            return Optional.empty();
        }
    }

    private Owner toOwner(Map<String, Object> user) {


        Map<String, Object> attributes = (Map<String, Object>) user.get("attributes");
        String phoneNumber = null;
        if (attributes != null) {
            List<String> phoneList = (List<String>) attributes.get("phoneNumber");
            if (phoneList != null && !phoneList.isEmpty()) {
                phoneNumber = phoneList.get(0);
            }
        }


        return new Owner(
                new Id<>(((String) user.get("id"))),
                (String) user.get("firstName"),
                (String) user.get("lastName"),
                new Email((String) user.get("email")),
                phoneNumber,
                (String) user.get("username"),
                null
        );
    }

    private String getMessage(Owner owner, WebClientResponseException e) {
        String message = e.getResponseBodyAsString();

        if (e.getStatusCode().equals(HttpStatus.CONFLICT)) {

            if (e.getResponseBodyAsString().contains("email")) {
                message = "User Already exists with this email: {%s}".formatted(owner.getEmail().email());
                throw new OwnerAlreadyExistInKeycloakException(message);
            }

            if (e.getResponseBodyAsString().contains("username")) {
                message = "User Already exists with this username: {%s}".formatted(owner.getUsername());
                throw new OwnerAlreadyExistInKeycloakException(message);
            }

        }
        return message;
    }

    private String getAdminAccessToken() {
        Map<String, Object> adminTokenResponse = webClient.post()
                .uri("http://localhost:8180/realms/master/protocol/openid-connect/token")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .body(BodyInserters
                        .fromFormData("grant_type", "password")
                        .with("client_id", "admin-cli")
                        .with("username", "admin")
                        .with("password", "admin"))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .block();
        return (String) adminTokenResponse.get("access_token");
    }

    private void postUserRole(String username, String roleName, String adminAccessToken) {

        String roleId = getRoleId(roleName, adminAccessToken);

        String userId = webClient.get()
                .uri("http://localhost:8180/admin/realms/keepdishesgoing/users?username=" + username)
                .header("Authorization", "Bearer " + adminAccessToken)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Map<String, Object>>>() {
                })
                .block()
                .get(0).get("id").toString();

        // POST role mapping
        Map<String, Object> rolePayload = Map.of(
                "id", roleId,
                "name", roleName
        );

        webClient.post()
                .uri("http://localhost:8180/admin/realms/keepdishesgoing/users/" + userId + "/role-mappings/realm")
                .header("Authorization", "Bearer " + adminAccessToken)
                .bodyValue(List.of(rolePayload))
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    private String getRoleId(String roleName, String adminAccessToken) {
        Map<String, Object> role = webClient.get()
                .uri("http://localhost:8180/admin/realms/keepdishesgoing/roles/{roleName}", roleName)
                .header("Authorization", "Bearer " + adminAccessToken)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .block();

        return (String) role.get("id");
    }


}
