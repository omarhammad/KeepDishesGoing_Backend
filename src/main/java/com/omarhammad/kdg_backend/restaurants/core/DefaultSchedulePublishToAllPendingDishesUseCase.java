package com.omarhammad.kdg_backend.restaurants.core;

import com.omarhammad.kdg_backend.restaurants.core.exceptions.ListIsEmptyException;
import com.omarhammad.kdg_backend.restaurants.core.exceptions.NoDishesScheduledException;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.ScheduleTooSoonException;
import com.omarhammad.kdg_backend.restaurants.domain.Dish;
import com.omarhammad.kdg_backend.restaurants.domain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.EntityNotFoundException;
import com.omarhammad.kdg_backend.restaurants.ports.in.SchedulePublishCmd;
import com.omarhammad.kdg_backend.restaurants.ports.in.SchedulePublishToAllPendingDishesUseCase;
import com.omarhammad.kdg_backend.restaurants.ports.out.EditDishPort;
import com.omarhammad.kdg_backend.restaurants.ports.out.EditRestaurant;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadDishPort;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadRestaurantPort;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class DefaultSchedulePublishToAllPendingDishesUseCase implements SchedulePublishToAllPendingDishesUseCase {

    private final LoadDishPort loadDishPort;
    private final LoadRestaurantPort loadRestaurantPort;
    private final EditRestaurant editRestaurant;
    private final EditDishPort editDishPort;

    @Override
    public void schedulePublish(Id<Restaurant> restaurantId, SchedulePublishCmd cmd) {

        long scheduleDuration = Duration.between(LocalDateTime.now()
                , cmd.scheduleTime()).toMinutes();

        log.info("Duration is {}", scheduleDuration);
        if (scheduleDuration < 1L) {
            throw new ScheduleTooSoonException("Schedule time should be at least 1h from now");
        }


        Restaurant restaurant = loadRestaurantPort.findRestaurantById(restaurantId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Restaurant {%s} not found!".formatted(restaurantId.value())));

        List<Dish> dishes = loadDishPort.findDishesByRestaurantId(restaurantId)
                .stream()
                .filter(dish -> Objects.nonNull(dish.getDraft()))
                .toList();

        if (dishes.isEmpty()) {
            throw new ListIsEmptyException("Restaurant {%s} has no dishes drafts to publish!".formatted(restaurantId.value()));
        }

        for (Dish dish : dishes) {
            dish.setScheduledTime(cmd.scheduleTime());
            editDishPort.edit(restaurantId, dish);
        }

        restaurant.setHasScheduledPublish(true);
        editRestaurant.edit(restaurant);


    }
}
