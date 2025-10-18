package com.omarhammad.kdg_backend.restaurants.core;

import com.omarhammad.kdg_backend.restaurants.domain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.EntityNotFoundException;
import com.omarhammad.kdg_backend.restaurants.ports.in.PublishAllPendingDishesUseCase;
import com.omarhammad.kdg_backend.restaurants.ports.out.EditRestaurantPort;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadRestaurantPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DefaultPublishAllPendingDishesUseCase implements PublishAllPendingDishesUseCase {


    private final LoadRestaurantPort loadRestaurantPort;
    private EditRestaurantPort editRestaurantPort;

    @Override
    public void publishAllPendingDishes(Id<Restaurant> restaurantId) {

        Restaurant restaurant = loadRestaurantPort.findRestaurantById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant {%s} not found".formatted(restaurantId.value())));


        restaurant.publishAllDishesPendingChanges();
        editRestaurantPort.edit(restaurant);


    }
}
