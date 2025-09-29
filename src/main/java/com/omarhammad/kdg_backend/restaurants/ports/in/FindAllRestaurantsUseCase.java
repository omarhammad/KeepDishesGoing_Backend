package com.omarhammad.kdg_backend.restaurants.ports.in;

import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;

import java.util.List;

public interface FindAllRestaurantsUseCase {


    List<Restaurant> findAllRestaurants();
}
