package com.omarhammad.kdg_backend.restaurants.core;

import com.omarhammad.kdg_backend.restaurants.domain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.EntityNotFoundException;
import com.omarhammad.kdg_backend.restaurants.ports.in.FindRestaurantByIdUseCase;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadRestaurantPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DefaultFindRestaurantByIdUseCase implements FindRestaurantByIdUseCase {

    LoadRestaurantPort loadRestaurantPort;

    @Override
    public Restaurant findRestaurantById(Id<Restaurant> restaurantId) throws EntityNotFoundException {

        return loadRestaurantPort.findRestaurantById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant {%s} not found".formatted(restaurantId.value())));
    }

}
