package com.omarhammad.kdg_backend.restaurants.ports.out;

import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;

public interface SaveRestaurantPort {

    void save(Restaurant restaurant);

}
