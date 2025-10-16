package com.omarhammad.kdg_backend.restaurants.ports.out;

import com.omarhammad.kdg_backend.restaurants.domain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.Owner;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;

import java.util.List;
import java.util.Optional;

public interface LoadRestaurantPort {


    List<Restaurant> findAllRestaurants();

    Optional<Restaurant> findRestaurantById(Id<Restaurant> restaurantId);

    Optional<Restaurant> findRestaurantByOwnerId(Id<Owner> ownerId);


}
