package com.omarhammad.kdg_backend.restaurants.core;

import com.omarhammad.kdg_backend.restaurants.domain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.EntityNotFoundException;
import com.omarhammad.kdg_backend.restaurants.ports.in.CreateDishDraftCmd;
import com.omarhammad.kdg_backend.restaurants.ports.in.CreateDishDraftUseCase;
import com.omarhammad.kdg_backend.restaurants.ports.out.EditRestaurantPort;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadRestaurantPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DefaultCreateDishDraftUseCase implements CreateDishDraftUseCase {


    private final EditRestaurantPort editRestaurantPort;
    private final LoadRestaurantPort loadRestaurantPort;

    @Override
    public void createDish(Id<Restaurant> restaurantId, CreateDishDraftCmd cmd) throws EntityNotFoundException {

        Restaurant restaurant = loadRestaurantPort.findRestaurantById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant {%s} not found".formatted(restaurantId.value())));


        restaurant.createDish(
                cmd.name(),
                cmd.dishType(),
                cmd.foodTags(),
                cmd.description(),
                cmd.price(),
                cmd.pictureUrl());


        editRestaurantPort.edit(restaurant);

    }
}
