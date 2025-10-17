package com.omarhammad.kdg_backend.restaurants.ports.out;

import com.omarhammad.kdg_backend.restaurants.domain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.OrderProjection;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;

import java.util.Optional;

public interface LoadOrderProjection {


    Optional<OrderProjection> findOrderProjectionByIdAndRestaurantId(Id<OrderProjection> orderProjectionId, Id<Restaurant> restaurantId);
    Optional<OrderProjection> findByOrderId(Id<OrderProjection> orderProjectionId);
}
