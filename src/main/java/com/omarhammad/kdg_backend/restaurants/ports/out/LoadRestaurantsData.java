package com.omarhammad.kdg_backend.restaurants.ports.out;

import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;

import java.util.List;

public interface LoadRestaurantsData {


    List<Restaurant> findAllRestaurants();
}
