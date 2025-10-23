package com.omarhammad.kdg_backend.common.events.restaurantEvents;

import com.omarhammad.kdg_backend.common.events.DomainEvent;
import org.jmolecules.event.annotation.Externalized;

import java.time.LocalDateTime;

@Externalized("kdg.events::#{'restaurant.' + #this.restaurantId() + '.dish.out-of-stock.v1'}")
public record DishOutOfStockEvent(String dishId, String restaurantId,
                                  LocalDateTime occurredAt) implements DomainEvent {
    @Override
    public LocalDateTime occurredAt() {
        return this.occurredAt;
    }

}
