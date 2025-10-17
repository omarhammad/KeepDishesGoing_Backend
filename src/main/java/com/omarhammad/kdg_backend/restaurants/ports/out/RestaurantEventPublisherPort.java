package com.omarhammad.kdg_backend.restaurants.ports.out;

import com.omarhammad.kdg_backend.common.events.restaurantEvents.OrderDeclinedEvent;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;

public interface RestaurantEventPublisherPort {

    void publishRestaurantEvents(Restaurant restaurant);

    void publishOrderDeclined(OrderDeclinedEvent event);

}
