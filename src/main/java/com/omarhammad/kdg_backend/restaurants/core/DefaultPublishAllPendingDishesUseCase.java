package com.omarhammad.kdg_backend.restaurants.core;

import com.omarhammad.kdg_backend.restaurants.core.exceptions.ListIsEmptyException;
import com.omarhammad.kdg_backend.restaurants.domain.Dish;
import com.omarhammad.kdg_backend.restaurants.domain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.EntityNotFoundException;
import com.omarhammad.kdg_backend.restaurants.ports.in.PublishAllPendingDishesUseCase;
import com.omarhammad.kdg_backend.restaurants.ports.out.EditDishPort;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadDishesByRestaurantIdPort;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadRestaurantByIdPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class DefaultPublishAllPendingDishesUseCase implements PublishAllPendingDishesUseCase {


    private final LoadRestaurantByIdPort loadRestaurantByIdPort;
    private final LoadDishesByRestaurantIdPort loadDishesByRestaurantIdPort;
    private final EditDishPort editDishPort;

    @Override
    public void publishAllPendingDishes(Id<Restaurant> restaurantId) {

        Restaurant restaurant = loadRestaurantByIdPort.findRestaurantById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant {%s} not found".formatted(restaurantId.value())));

        List<Dish> dishes = loadDishesByRestaurantIdPort.findDishesByRestaurantId(restaurantId);

        if (Objects.isNull(dishes) || dishes.isEmpty())
            throw new ListIsEmptyException("Restaurant {%s} has no dishes".formatted(restaurant.getName()));

        for (Dish dish : dishes) {
            if(dish.isPublished()) continue;

            dish.setPublished(true);
            dish.setPublishTime(LocalDateTime.now());
            editDishPort.edit(restaurantId, dish);
        }

    }
}
