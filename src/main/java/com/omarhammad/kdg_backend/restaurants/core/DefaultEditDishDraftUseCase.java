package com.omarhammad.kdg_backend.restaurants.core;

import com.omarhammad.kdg_backend.restaurants.domain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.Dish;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.EntityNotFoundException;
import com.omarhammad.kdg_backend.restaurants.ports.in.EditDishDraftCmd;
import com.omarhammad.kdg_backend.restaurants.ports.in.EditDishDraftUseCase;
import com.omarhammad.kdg_backend.restaurants.ports.out.EditRestaurantPort;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadRestaurantPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DefaultEditDishDraftUseCase implements EditDishDraftUseCase {

    private final LoadRestaurantPort loadRestaurantPort;
    private final EditRestaurantPort editRestaurantPort;

    @Override
    public void editDishDraft(Id<Restaurant> restaurantId, Id<Dish> dishId, EditDishDraftCmd cmd) {


        Restaurant restaurant = loadRestaurantPort.findRestaurantById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant {%s} not found".formatted(restaurantId.value())));


        restaurant.editDishDraft(dishId, cmd.name(),
                cmd.dishType(),
                cmd.foodTags(),
                cmd.description(),
                cmd.price(),
                cmd.pictureUrl());

        editRestaurantPort.edit(restaurant);

    }
}
