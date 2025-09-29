package com.omarhammad.kdg_backend.restaurants.ports.out;

import com.omarhammad.kdg_backend.common.sharedDomain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.Dish;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;

import java.util.List;
import java.util.Optional;

public interface LoadDishesByRestaurantIdPort {

    Optional<List<Dish>> findDishesByRestaurantId(Id<Restaurant> restaurantId);
}
