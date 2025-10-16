package com.omarhammad.kdg_backend.common.events.restaurantEvents;

import com.omarhammad.kdg_backend.common.events.DomainEvent;
import org.springframework.modulith.events.Externalized;

import java.time.LocalDateTime;

@Externalized("kdg.exchange:#{'kdg.' + #this.orderId() + '.order.rejected'}")
public record OrderRejectedEvent(String orderId, String reason, LocalDateTime occurredAt) implements DomainEvent {
    @Override
    public LocalDateTime occurredAt() {
        return this.occurredAt;
    }
}
