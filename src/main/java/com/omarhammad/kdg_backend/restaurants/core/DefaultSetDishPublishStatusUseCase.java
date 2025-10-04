package com.omarhammad.kdg_backend.restaurants.core;

import com.omarhammad.kdg_backend.restaurants.domain.Dish;
import com.omarhammad.kdg_backend.restaurants.domain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.EntityNotFoundException;
import com.omarhammad.kdg_backend.restaurants.ports.in.SetDishPublishStatusUseCase;
import com.omarhammad.kdg_backend.restaurants.ports.in.SetDishPublishStatusCmd;
import com.omarhammad.kdg_backend.restaurants.ports.out.EditDishPort;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadDishPort;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadRestaurantPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DefaultSetDishPublishStatusUseCase implements SetDishPublishStatusUseCase {

    private final LoadRestaurantPort loadRestaurantPort;
    private final LoadDishPort loadDishPort;
    private final EditDishPort editDishPort;

    @Override
    public void setPublishDishStatus(Id<Restaurant> restaurantId, Id<Dish> dishId, SetDishPublishStatusCmd cmd) {

        loadRestaurantPort.findRestaurantById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant {%s} not found".formatted(restaurantId.value())));

        Dish dish = loadDishPort.findById(restaurantId, dishId)
                .orElseThrow(() -> new EntityNotFoundException("Dish {%s} not found".formatted(dishId)));

        if (cmd.isPublished()) {
            dish.publish();
        } else {
            dish.unpublish();
        }

        editDishPort.edit(restaurantId, dish);
    }
}
