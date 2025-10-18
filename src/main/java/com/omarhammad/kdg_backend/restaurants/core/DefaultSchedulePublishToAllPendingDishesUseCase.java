package com.omarhammad.kdg_backend.restaurants.core;

import com.omarhammad.kdg_backend.restaurants.domain.exceptions.ScheduleTooSoonException;
import com.omarhammad.kdg_backend.restaurants.domain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.EntityNotFoundException;
import com.omarhammad.kdg_backend.restaurants.ports.in.SchedulePublishCmd;
import com.omarhammad.kdg_backend.restaurants.ports.in.SchedulePublishToAllPendingDishesUseCase;
import com.omarhammad.kdg_backend.restaurants.ports.out.EditRestaurantPort;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadRestaurantPort;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
@Service
@AllArgsConstructor
public class DefaultSchedulePublishToAllPendingDishesUseCase implements SchedulePublishToAllPendingDishesUseCase {

    private final LoadRestaurantPort loadRestaurantPort;
    private final EditRestaurantPort editRestaurantPort;

    @Override
    public void schedulePublish(Id<Restaurant> restaurantId, SchedulePublishCmd cmd) {

        long scheduleDuration = Duration.between(LocalDateTime.now(), cmd.scheduleTime()).toHours();

        if (scheduleDuration < 1L) {
            throw new ScheduleTooSoonException("Schedule time should be at least 1h from now");
        }


        Restaurant restaurant = loadRestaurantPort.findRestaurantById(restaurantId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Restaurant {%s} not found!".formatted(restaurantId.value())));

        restaurant.schedulePublishToALlPendingDishes(cmd.scheduleTime());
        editRestaurantPort.edit(restaurant);


    }
}
