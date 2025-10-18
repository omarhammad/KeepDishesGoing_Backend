package com.omarhammad.kdg_backend.restaurants.core;

import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.EntityNotFoundException;
import com.omarhammad.kdg_backend.restaurants.ports.in.SetDishPublishStatusUseCase;
import com.omarhammad.kdg_backend.restaurants.ports.in.SetDishPublishStatusCmd;
import com.omarhammad.kdg_backend.restaurants.ports.out.EditRestaurantPort;
import com.omarhammad.kdg_backend.restaurants.ports.out.EventPublisherPort;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadRestaurantPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DefaultSetDishPublishStatusUseCase implements SetDishPublishStatusUseCase {

    private final LoadRestaurantPort loadRestaurantPort;
    private final EditRestaurantPort editRestaurantPort;
    private final EventPublisherPort eventPublisherPort;


    @Override
    public void setPublishDishStatus(SetDishPublishStatusCmd cmd) {

        Restaurant restaurant = loadRestaurantPort.findRestaurantById(cmd.restaurantId())
                .orElseThrow(() -> new EntityNotFoundException("Restaurant {%s} not found".formatted(cmd.restaurantId().value())));


        if (cmd.isPublished()) {
            restaurant.publishDish(cmd.dishId());
        } else {
            restaurant.unpublishDish(cmd.dishId());
        }


        editRestaurantPort.edit(restaurant);
        eventPublisherPort.publishRestaurantEvents(restaurant);

    }
}
