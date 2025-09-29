package com.omarhammad.kdg_backend.restaurants.ports.in;

import com.omarhammad.kdg_backend.common.sharedDomain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.Dish;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;

import java.util.List;

public interface FindDishesByRestaurantIdUseCase {

    List<Dish> findDishesByRestaurantId(Id<Restaurant> restaurantId);
}
