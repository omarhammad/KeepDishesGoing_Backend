package com.omarhammad.kdg_backend.restaurants.adapters.out.geocodingAdapter;

import com.omarhammad.kdg_backend.restaurants.domain.Address;
import com.omarhammad.kdg_backend.restaurants.domain.Coordinates;
import com.omarhammad.kdg_backend.restaurants.ports.out.GeocodingPort;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Component
public class NominatimGeocodingAdapter implements GeocodingPort {

    private final WebClient webClient = WebClient.create("https://nominatim.openstreetmap.org");

    @Override
    public Coordinates geocode(Address address) {
        String query = "%s %d, %s %s".formatted(address.street(), address.number(), address.postalCode(),
                address.city());

        var response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search")
                        .queryParam("q", query)
                        .queryParam("format", "json")
                        .queryParam("limit", 1)
                        .build())
                .retrieve()
                .bodyToMono(List.class)
                .block();

        if (response == null || response.isEmpty()) return new Coordinates(0.0, 0.0);

        Map<String, Object> location = (Map<String, Object>) response.get(0);
        return new Coordinates(
                Double.parseDouble((String) location.get("lon")),
                Double.parseDouble((String) location.get("lat"))
        );
    }
}
