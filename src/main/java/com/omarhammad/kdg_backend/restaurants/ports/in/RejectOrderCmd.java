package com.omarhammad.kdg_backend.restaurants.ports.in;

import com.omarhammad.kdg_backend.restaurants.domain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.OrderProjection;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;

public record RejectOrderCmd(Id<Restaurant> restaurantId, Id<OrderProjection> orderProjectionId, String reason) {

}
