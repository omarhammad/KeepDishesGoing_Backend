package com.omarhammad.kdg_backend.restaurants.ports.out;

import com.omarhammad.kdg_backend.common.sharedDomain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.Owner;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;

import java.util.Optional;

public interface LoadRestaurantByOwnerIdPort {

    Optional<Restaurant> loadRestaurantByOwnerId(Id<Owner> ownerId);
}
