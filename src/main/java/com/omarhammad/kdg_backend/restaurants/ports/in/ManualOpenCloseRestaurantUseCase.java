package com.omarhammad.kdg_backend.restaurants.ports.in;

import com.omarhammad.kdg_backend.restaurants.domain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;

public interface ManualOpenCloseRestaurantUseCase {

    void open(Id<Restaurant> restaurantId);

    void close(Id<Restaurant> restaurantId);

    void auto(Id<Restaurant> restaurantId);

}
