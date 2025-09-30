package com.omarhammad.kdg_backend.restaurants.core;

import com.omarhammad.kdg_backend.restaurants.domain.Dish;
import com.omarhammad.kdg_backend.restaurants.domain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.EntityNotFoundException;
import com.omarhammad.kdg_backend.restaurants.ports.in.SetDishStockStatusCmd;
import com.omarhammad.kdg_backend.restaurants.ports.in.SetDishStockStatusUseCase;
import com.omarhammad.kdg_backend.restaurants.ports.out.EditDishPort;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadDishByIdPort;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadRestaurantByIdPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DefaultSetDishStockStatusUseCase implements SetDishStockStatusUseCase {

    private LoadRestaurantByIdPort loadRestaurantByIdPort;
    private LoadDishByIdPort loadDishByIdPort;
    private EditDishPort editDishPort;

    @Override
    public void setDishStockStatus(Id<Restaurant> restaurantId, Id<Dish> dishId, SetDishStockStatusCmd cmd) {

        loadRestaurantByIdPort.findRestaurantById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant {%s} not found".formatted(restaurantId.value())));

        Dish dish = loadDishByIdPort.findById(restaurantId, dishId)
                .orElseThrow(() -> new EntityNotFoundException("Dish {%s} not found".formatted(dishId)));

        dish.setInStock(cmd.isInStock());
        editDishPort.edit(restaurantId, dish);

    }
}
