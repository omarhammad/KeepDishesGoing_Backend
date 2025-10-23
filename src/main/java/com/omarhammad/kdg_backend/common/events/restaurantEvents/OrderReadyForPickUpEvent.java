package com.omarhammad.kdg_backend.common.events.restaurantEvents;

import com.omarhammad.kdg_backend.common.events.DomainEvent;
import org.jmolecules.event.annotation.Externalized;

import java.time.LocalDateTime;

@Externalized("kdg.events::#{'restaurant.' + #this.restaurantId() +'.order.ready.v1'}")
public record OrderReadyForPickUpEvent(String eventId, String orderId, String restaurantId,
                                       LocalDateTime occurredAt) implements DomainEvent {
    @Override
    public LocalDateTime occurredAt() {
        return this.occurredAt;
    }
}
