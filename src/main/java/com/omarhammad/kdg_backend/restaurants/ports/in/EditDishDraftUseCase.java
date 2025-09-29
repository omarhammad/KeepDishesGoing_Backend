package com.omarhammad.kdg_backend.restaurants.ports.in;

import com.omarhammad.kdg_backend.common.sharedDomain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.Dish;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;

public interface EditDishDraftUseCase {

    void editDish(Id<Restaurant> restaurantId, Id<Dish> dishId, EditDishDraftCmd cmd);
}
