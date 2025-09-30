package com.omarhammad.kdg_backend.restaurants.ports.in;

import com.omarhammad.kdg_backend.restaurants.domain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.EntityNotFoundException;

public interface FindRestaurantByIdUseCase {

    Restaurant findRestaurantById(Id<Restaurant> restaruantId) throws EntityNotFoundException;
}
