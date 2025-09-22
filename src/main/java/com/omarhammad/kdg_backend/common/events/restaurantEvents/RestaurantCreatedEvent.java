package com.omarhammad.kdg_backend.common.events.restaurantEvents;

import java.util.UUID;

public record RestaurantCreatedEvent(UUID restaurantId, Long OwnerId) {
}
