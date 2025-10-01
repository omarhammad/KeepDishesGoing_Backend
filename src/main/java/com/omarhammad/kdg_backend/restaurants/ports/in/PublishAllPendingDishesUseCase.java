package com.omarhammad.kdg_backend.restaurants.ports.in;

import com.omarhammad.kdg_backend.restaurants.domain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;

public interface PublishAllPendingDishesUseCase {

    void publishAllPendingDishes(Id<Restaurant> restaurantId);

}
