package com.omarhammad.kdg_backend.restaurants.core;

import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.EntityNotFoundException;
import com.omarhammad.kdg_backend.restaurants.ports.in.SetDishStockStatusCmd;
import com.omarhammad.kdg_backend.restaurants.ports.in.SetDishStockStatusUseCase;
import com.omarhammad.kdg_backend.restaurants.ports.out.EditRestaurantPort;
import com.omarhammad.kdg_backend.restaurants.ports.out.EventPublisherPort;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadRestaurantPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DefaultSetDishStockStatusUseCase implements SetDishStockStatusUseCase {

    private final EventPublisherPort eventPublisherPort;
    private LoadRestaurantPort loadRestaurantPort;
    private EditRestaurantPort editRestaurantPort;

    @Override
    public void setDishStockStatus(SetDishStockStatusCmd cmd) {

        Restaurant restaurant = loadRestaurantPort.findRestaurantById(cmd.restaurantId())
                .orElseThrow(() -> new EntityNotFoundException("Restaurant {%s} not found".formatted(cmd.restaurantId().value())));


        if (cmd.isInStock()) {
            restaurant.setDishInStockStatus(cmd.dishId());
        } else {
            restaurant.setDishOutOfStockStatus(cmd.dishId());
        }
        editRestaurantPort.edit(restaurant);
        eventPublisherPort.publishRestaurantEvents(restaurant);

    }
}
