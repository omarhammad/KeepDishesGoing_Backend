package com.omarhammad.kdg_backend.restaurants.core;

import com.omarhammad.kdg_backend.restaurants.domain.Dish;
import com.omarhammad.kdg_backend.restaurants.domain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.EntityNotFoundException;
import com.omarhammad.kdg_backend.restaurants.ports.in.SetDishPublishStatusUseCase;
import com.omarhammad.kdg_backend.restaurants.ports.in.SetDishPublishStatusCmd;
import com.omarhammad.kdg_backend.restaurants.ports.out.EditDishPort;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadDishByIdPort;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadRestaurantByIdPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class DefaultSetDishPublishStatusUseCase implements SetDishPublishStatusUseCase {

    private final LoadRestaurantByIdPort loadRestaurantByIdPort;
    private final LoadDishByIdPort loadDishByIdPort;
    private final EditDishPort editDishPort;

    @Override
    public void setPublishDishStatus(Id<Restaurant> restaurantId, Id<Dish> dishId, SetDishPublishStatusCmd cmd) {

        loadRestaurantByIdPort.findRestaurantById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant {%s} not found".formatted(restaurantId.value())));

        Dish dish = loadDishByIdPort.findById(restaurantId, dishId)
                .orElseThrow(() -> new EntityNotFoundException("Dish {%s} not found".formatted(dishId)));

        dish.setPublished(cmd.isPublished());
        dish.setPublishTime(cmd.isPublished() ? LocalDateTime.now() : null);


        editDishPort.edit(restaurantId, dish);
    }
}
