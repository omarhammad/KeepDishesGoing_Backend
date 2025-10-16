package com.omarhammad.kdg_backend.restaurants.adapters.out.messaging;

import com.omarhammad.kdg_backend.common.events.restaurantEvents.OrderAcceptedEvent;
import com.omarhammad.kdg_backend.common.events.restaurantEvents.OrderRejectedEvent;
import com.omarhammad.kdg_backend.restaurants.ports.out.RestaurantEventPublisherPort;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RestaurantEventPublisherAdapter implements RestaurantEventPublisherPort {


    private final ApplicationEventPublisher applicationEventPublisher;


    @Override
    public void publishOrderAccepted(OrderAcceptedEvent event) {
        applicationEventPublisher.publishEvent(event);
    }

    @Override
    public void publishOrderRejected(OrderRejectedEvent event) {
        applicationEventPublisher.publishEvent(event);
    }
}
