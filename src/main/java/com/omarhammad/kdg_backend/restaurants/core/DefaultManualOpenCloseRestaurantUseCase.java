package com.omarhammad.kdg_backend.restaurants.core;

import com.omarhammad.kdg_backend.restaurants.domain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.domain.enums.OpeningStatus;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.EntityNotFoundException;
import com.omarhammad.kdg_backend.restaurants.ports.in.ManualOpenCloseRestaurantUseCase;
import com.omarhammad.kdg_backend.restaurants.ports.out.EditRestaurant;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadRestaurantPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DefaultManualOpenCloseRestaurantUseCase implements ManualOpenCloseRestaurantUseCase {


    private EditRestaurant editRestaurant;
    private LoadRestaurantPort loadRestaurantPort;

    @Override
    public void open(Id<Restaurant> restaurantId) {

        Restaurant restaurant = getRestaurant(restaurantId);
        restaurant.setManualOpening(OpeningStatus.OPEN);
        editRestaurant.edit(restaurant);

    }

    @Override
    public void close(Id<Restaurant> restaurantId) {

        Restaurant restaurant = getRestaurant(restaurantId);
        restaurant.setManualOpening(OpeningStatus.CLOSE);
        editRestaurant.edit(restaurant);

    }

    @Override
    public void auto(Id<Restaurant> restaurantId) {

        Restaurant restaurant = getRestaurant(restaurantId);
        restaurant.setManualOpening(OpeningStatus.AUTO);
        editRestaurant.edit(restaurant);
    }

    private Restaurant getRestaurant(Id<Restaurant> restaurantId) {
        return loadRestaurantPort.findRestaurantById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant {%s} not found".formatted(restaurantId.value())));
    }
}
