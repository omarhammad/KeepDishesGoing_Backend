package com.omarhammad.kdg_backend.common.events.deliveryEvents;

import java.time.LocalDateTime;

public record OrderPickedUpEvent(String eventId, String orderId, String restaurantId, LocalDateTime occurredAt) {


}
