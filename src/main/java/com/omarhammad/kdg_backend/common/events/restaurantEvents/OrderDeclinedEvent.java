package com.omarhammad.kdg_backend.common.events.restaurantEvents;

import com.omarhammad.kdg_backend.common.events.DomainEvent;
import org.jmolecules.event.annotation.Externalized;

import java.time.LocalDateTime;

@Externalized("kdg.events::#{'restaurant.' + #this.restaurantId() + '.order.declined.v1'}")
public record OrderDeclinedEvent(String orderId, String restaurantId, String declinedMsg,
                                 LocalDateTime occurredAt) implements DomainEvent {

    public static String ERROR_RESTAURANT_NOT_FOUND = "RESTAURANT_NOT_FOUND";
    public static String ERROR_ORDER_ALREADY_EXIST = "ORDER_ALREADY_EXIST";
    public static String ERROR_RESPONSE_TIME_EXCEEDED = "RESPONSE_TIME_EXCEEDED";

    @Override
    public LocalDateTime occurredAt() {
        return this.occurredAt;
    }

}
