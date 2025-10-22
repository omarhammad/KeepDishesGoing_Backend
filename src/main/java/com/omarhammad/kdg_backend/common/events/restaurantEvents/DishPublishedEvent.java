package com.omarhammad.kdg_backend.common.events.restaurantEvents;

import com.omarhammad.kdg_backend.common.events.DomainEvent;
import org.jmolecules.event.annotation.Externalized;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Externalized("kdg.exchange::#{'kdg.' + #this.dishId() + '.dish.published'}")
public record DishPublishedEvent(String dishId, String restaurantId, BigDecimal price,
                                 LocalDateTime occurredAt) implements DomainEvent {
    @Override
    public LocalDateTime occurredAt() {
        return this.occurredAt;
    }
}
