package com.omarhammad.kdg_backend.restaurants.ports.out;

import com.omarhammad.kdg_backend.common.events.restaurantEvents.OrderAcceptedEvent;
import com.omarhammad.kdg_backend.common.events.restaurantEvents.OrderRejectedEvent;

public interface RestaurantEventPublisherPort {


    void publishOrderAccepted(OrderAcceptedEvent event);

    void publishOrderRejected(OrderRejectedEvent event);
}
