package com.omarhammad.kdg_backend.restaurants.ports.in;

import com.omarhammad.kdg_backend.restaurants.domain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.OrderProjection;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;

import java.time.LocalDateTime;

public record OrderPickedupProjectorCmd(Id<OrderProjection> orderId, Id<Restaurant> restaurantId,
                                        LocalDateTime occurredAt) {
}
