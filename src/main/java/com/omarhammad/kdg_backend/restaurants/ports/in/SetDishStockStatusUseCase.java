package com.omarhammad.kdg_backend.restaurants.ports.in;

import com.omarhammad.kdg_backend.restaurants.domain.Dish;
import com.omarhammad.kdg_backend.restaurants.domain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;

public interface SetDishStockStatusUseCase {

    void setDishStockStatus(Id<Restaurant> restaurantId, Id<Dish> dishId, SetDishStockStatusCmd cmd);

}
