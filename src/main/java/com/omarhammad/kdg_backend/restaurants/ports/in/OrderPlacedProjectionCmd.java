package com.omarhammad.kdg_backend.restaurants.ports.in;

import com.omarhammad.kdg_backend.restaurants.domain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.IncomingOrder;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;

import java.time.LocalDateTime;

public record OrderPlacedProjectionCmd(Id<IncomingOrder> orderId, Id<Restaurant> restaurantId, LocalDateTime occurredAt) {
}
