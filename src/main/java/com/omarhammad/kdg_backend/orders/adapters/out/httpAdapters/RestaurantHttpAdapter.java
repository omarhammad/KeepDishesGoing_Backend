package com.omarhammad.kdg_backend.orders.adapters.out.httpAdapters;

import com.omarhammad.kdg_backend.orders.domain.Id;
import com.omarhammad.kdg_backend.orders.ports.out.RestaurantAvailabilityPort;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class RestaurantHttpAdapter implements RestaurantAvailabilityPort {

    private final WebClient webClient;

    public RestaurantHttpAdapter() {
        webClient = WebClient.create("http://localhost:8080/api/restaurants/");

    }

    @Override
    public boolean isRestaurantOpen(Id restaurantId) {
        return Boolean.TRUE.equals(webClient.get()
                .uri("/{id}/open-status", restaurantId.value())
                .retrieve()
                .bodyToMono(Boolean.class)
                .block());
    }

}
