package com.omarhammad.kdg_backend.restaurants.core;

import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.ports.in.TriggerScheduledPublishAllPendingDishesUseCase;
import com.omarhammad.kdg_backend.restaurants.ports.out.EditRestaurantPort;
import com.omarhammad.kdg_backend.restaurants.ports.out.EventPublisherPort;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadRestaurantPort;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class DefaultTriggerScheduledPublishAllPendingDishesUseCase implements TriggerScheduledPublishAllPendingDishesUseCase {

    private final LoadRestaurantPort loadRestaurantPort;
    private final EditRestaurantPort editRestaurantPort;
    private final EventPublisherPort eventPublisherPort;

    @Override
    public void triggerScheduledPublish() {

        List<Restaurant> restaurants = loadRestaurantPort.findAllRestaurants()
                .stream()
                .filter(Restaurant::hasScheduledPublish)
                .toList();

        if (restaurants.isEmpty()) return;
        for (Restaurant restaurant : restaurants) {
            restaurant.publishAllScheduledDishes();
            editRestaurantPort.edit(restaurant);
            eventPublisherPort.publishRestaurantEvents(restaurant);
        }
    }
}