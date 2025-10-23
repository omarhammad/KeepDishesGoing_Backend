package com.omarhammad.kdg_backend.common.events.ordersEvents;

import com.omarhammad.kdg_backend.common.events.DomainEvent;
import org.jmolecules.event.annotation.Externalized;

import java.time.LocalDateTime;

@Externalized("kdg.events::#{'order.' + #this.orderId + '.order.placed.v1'}")
public record OrderPlacedEvent(String orderId, String restaurantId, Address dropOfAddress,
                               LocalDateTime occurredAt) implements DomainEvent {
    @Override
    public LocalDateTime occurredAt() {
        return this.occurredAt;
    }

    public record Address(String street,
                          int number,
                          String postalCode,
                          String city,
                          String country) {

    }
}
