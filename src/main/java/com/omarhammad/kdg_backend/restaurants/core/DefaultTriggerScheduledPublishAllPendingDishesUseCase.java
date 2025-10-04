package com.omarhammad.kdg_backend.restaurants.core;

import com.omarhammad.kdg_backend.restaurants.domain.Dish;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.ports.in.TriggerScheduledPublishAllPendingDishesUseCase;
import com.omarhammad.kdg_backend.restaurants.ports.out.EditDishPort;
import com.omarhammad.kdg_backend.restaurants.ports.out.EditRestaurant;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadDishPort;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadRestaurantPort;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static java.util.Arrays.stream;

@Slf4j
@Service
@AllArgsConstructor
public class DefaultTriggerScheduledPublishAllPendingDishesUseCase implements TriggerScheduledPublishAllPendingDishesUseCase {

    private final LoadRestaurantPort loadRestaurantPort;
    private final LoadDishPort loadDishPort;
    private final EditDishPort editDishPort;
    private final EditRestaurant editRestaurant;

    @Override
    public void triggerScheduledPublish() {

        List<Restaurant> restaurants = loadRestaurantPort.findAllRestaurants()
                .stream()
                .filter(Restaurant::hasScheduledPublish)
                .toList();

        if (restaurants.isEmpty()) return;
        for (Restaurant restaurant : restaurants) {
            List<Dish> dishes = loadDishPort.findDishesByRestaurantId(restaurant.getId())
                    .stream()
                    .filter(dish -> Objects.nonNull(dish.getScheduledTime()))
                    .toList();


            log.info("Scheduler entered! for {} dishes", dishes.size());
            boolean stillHasScheduled = false;
            for (Dish dish : dishes) {
                if (dish.getScheduledTime().isAfter(LocalDateTime.now())) {
                    stillHasScheduled = true;
                    continue;
                }
                dish.publish();
                editDishPort.edit(restaurant.getId(), dish);
            }
            restaurant.setHasScheduledPublish(stillHasScheduled);
            editRestaurant.edit(restaurant);
        }


    }
}
