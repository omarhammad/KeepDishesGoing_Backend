package com.omarhammad.kdg_backend.restaurants.ports.out;

import com.omarhammad.kdg_backend.restaurants.domain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.Dish;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;

import java.util.List;

public interface LoadDishesByRestaurantIdPort {

    List<Dish> findDishesByRestaurantId(Id<Restaurant> restaurantId);
}
