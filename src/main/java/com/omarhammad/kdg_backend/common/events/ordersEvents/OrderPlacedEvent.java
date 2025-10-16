package com.omarhammad.kdg_backend.common.events.ordersEvents;

import com.omarhammad.kdg_backend.common.events.DomainEvent;
import org.springframework.modulith.events.Externalized;

import java.time.LocalDateTime;

@Externalized("kdg.exchange:#{'kdg.' + #this.orderId() + '.order.placed'}")
public record OrderPlacedEvent(String orderId, String restaurantId, LocalDateTime occurredAt) implements DomainEvent {
    @Override
    public LocalDateTime occurredAt() {
        return this.occurredAt;
    }
}
