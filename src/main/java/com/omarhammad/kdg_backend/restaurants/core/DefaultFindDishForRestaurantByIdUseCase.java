package com.omarhammad.kdg_backend.restaurants.core;

import com.omarhammad.kdg_backend.common.sharedDomain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.Dish;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.EntityNotFoundException;
import com.omarhammad.kdg_backend.restaurants.ports.in.FindDishForRestaurantByIdUseCase;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadDishByIdPort;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadRestaurantByIdPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DefaultFindDishForRestaurantByIdUseCase implements FindDishForRestaurantByIdUseCase {

    private LoadDishByIdPort loadDishByIdPort;
    private LoadRestaurantByIdPort loadRestaurantByIdPort;

    @Override
    public Dish findDishOfARestaurantById(Id<Restaurant> restaurantId, Id<Dish> dishId) {

        loadRestaurantByIdPort.findRestaurantById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant {%s} not found".formatted(restaurantId.value())));

        return loadDishByIdPort.findById(restaurantId, dishId)
                .orElseThrow(() -> new EntityNotFoundException("Dish {%s} not found".formatted(dishId.value())));
    }

}
