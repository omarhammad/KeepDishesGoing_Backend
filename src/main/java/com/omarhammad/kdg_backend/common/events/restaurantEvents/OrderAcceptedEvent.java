package com.omarhammad.kdg_backend.common.events.restaurantEvents;

import com.omarhammad.kdg_backend.common.events.DomainEvent;
import org.jmolecules.event.annotation.Externalized;

import java.time.LocalDateTime;

@Externalized("kdg.events::#{'restaurant.' + #this.restaurantId() + '.order.accepted.v1'}")
public record OrderAcceptedEvent(
        String eventId,
        String orderId,
        String restaurantId,
        LocalDateTime occurredAt,
        Address pickupAddress,
        Coordinates pickupCoordinates,
        Address dropoffAddress,
        Coordinates dropoffCoordinates
) implements DomainEvent {

    @Override
    public LocalDateTime occurredAt() {
        return this.occurredAt;
    }

    public record Address(
            String street,
            String number,
            String postalCode,
            String city
    ) {
    }

    public record Coordinates(
            double lat,
            double lng
    ) {
    }
}
