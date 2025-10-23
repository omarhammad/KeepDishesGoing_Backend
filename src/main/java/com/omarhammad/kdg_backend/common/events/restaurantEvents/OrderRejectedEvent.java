package com.omarhammad.kdg_backend.common.events.restaurantEvents;

import com.omarhammad.kdg_backend.common.events.DomainEvent;
import org.jmolecules.event.annotation.Externalized;

import java.time.LocalDateTime;

@Externalized("kdg.events::#{'restaurant.' + #this.restaurantId() + '.order.rejected.v1'}")
public record OrderRejectedEvent(String orderId, String restaurantId, String reason,
                                 LocalDateTime occurredAt) implements DomainEvent {
    @Override
    public LocalDateTime occurredAt() {
        return this.occurredAt;
    }
}
