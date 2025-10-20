package com.omarhammad.kdg_backend.orders.ports.out;

import com.omarhammad.kdg_backend.orders.domain.Id;

public interface RestaurantAvailabilityPort {


    boolean isRestaurantOpen(Id restaurantId);
}
