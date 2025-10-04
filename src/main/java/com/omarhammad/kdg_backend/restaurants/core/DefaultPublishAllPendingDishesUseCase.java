package com.omarhammad.kdg_backend.restaurants.core;

import com.omarhammad.kdg_backend.restaurants.core.exceptions.ListIsEmptyException;
import com.omarhammad.kdg_backend.restaurants.domain.Dish;
import com.omarhammad.kdg_backend.restaurants.domain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.EntityNotFoundException;
import com.omarhammad.kdg_backend.restaurants.ports.in.PublishAllPendingDishesUseCase;
import com.omarhammad.kdg_backend.restaurants.ports.out.EditDishPort;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadDishPort;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadRestaurantPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class DefaultPublishAllPendingDishesUseCase implements PublishAllPendingDishesUseCase {


    private final LoadRestaurantPort loadRestaurantPort;
    private final LoadDishPort loadDishPort;
    private final EditDishPort editDishPort;

    @Override
    public void publishAllPendingDishes(Id<Restaurant> restaurantId) {

        Restaurant restaurant = loadRestaurantPort.findRestaurantById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant {%s} not found".formatted(restaurantId.value())));

        List<Dish> dishes = loadDishPort.findDishesByRestaurantId(restaurantId).stream()
                .filter(dish -> Objects.nonNull(dish.getDraft()))
                .toList();

        if (dishes.isEmpty())
            throw new ListIsEmptyException("Restaurant {%s} has no dishes drafts to publish".formatted(restaurant.getName()));


        for (Dish dish : dishes) {
            dish.publish();
            editDishPort.edit(restaurantId, dish);
        }

    }
}
