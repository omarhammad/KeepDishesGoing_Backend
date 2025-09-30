package com.omarhammad.kdg_backend.restaurants.ports.out;

import com.omarhammad.kdg_backend.restaurants.domain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;

import java.util.Optional;

public interface LoadRestaurantByIdPort {

    Optional<Restaurant> findRestaurantById(Id<Restaurant> restaurantId);

}
