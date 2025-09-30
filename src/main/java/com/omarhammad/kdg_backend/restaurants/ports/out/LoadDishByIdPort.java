package com.omarhammad.kdg_backend.restaurants.ports.out;

import com.omarhammad.kdg_backend.restaurants.domain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.Dish;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;

import java.util.Optional;

public interface LoadDishByIdPort {

    Optional<Dish> findById(Id<Restaurant> restaurantId, Id<Dish> dishId);

}
