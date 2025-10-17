package com.omarhammad.kdg_backend.restaurants.adapters.out.messaging;

import com.omarhammad.kdg_backend.common.events.restaurantEvents.OrderDeclinedEvent;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.ports.out.EventPublisherPort;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EventPublisherAdapter implements EventPublisherPort {


    private final ApplicationEventPublisher applicationEventPublisher;


    @Override
    @Transactional
    public void publishRestaurantEvents(Restaurant restaurant) {
        restaurant.getDomainEvents().forEach(applicationEventPublisher::publishEvent);
    }

    @Override
    @Transactional
    public void publishOrderDeclined(OrderDeclinedEvent event) {
        applicationEventPublisher.publishEvent(event);

    }
}
