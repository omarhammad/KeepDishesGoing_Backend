package com.omarhammad.kdg_backend.restaurants.core;

import com.omarhammad.kdg_backend.restaurants.domain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.Dish;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.EntityNotFoundException;
import com.omarhammad.kdg_backend.restaurants.ports.in.FindDishForRestaurantByIdUseCase;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadRestaurantPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DefaultFindDishForRestaurantByIdUseCase implements FindDishForRestaurantByIdUseCase {

    private LoadRestaurantPort loadRestaurantPort;

    @Override
    public Dish findDishOfARestaurantById(Id<Restaurant> restaurantId, Id<Dish> dishId) {

        Restaurant restaurant = loadRestaurantPort.findRestaurantById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant {%s} not found".formatted(restaurantId.value())));

        return restaurant.findDishById(dishId);

    }

}
