package com.omarhammad.kdg_backend.restaurants.core;

import com.omarhammad.kdg_backend.common.sharedDomain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.Dish;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.EntityNotFoundException;
import com.omarhammad.kdg_backend.restaurants.ports.in.EditDishDraftCmd;
import com.omarhammad.kdg_backend.restaurants.ports.in.EditDishDraftUseCase;
import com.omarhammad.kdg_backend.restaurants.ports.out.EditDishDraftPort;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadDishByIdPort;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadRestaurantByIdPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DefaultEditDishDraftUseCase implements EditDishDraftUseCase {

    private final EditDishDraftPort editDishDraftPort;
    private final LoadRestaurantByIdPort loadRestaurantByIdPort;
    private final LoadDishByIdPort loadDishByIdPort;

    @Override
    public void editDish(Id<Restaurant> restaurantId, Id<Dish> dishId, EditDishDraftCmd cmd) {


        loadRestaurantByIdPort.findRestaurantById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant {%s} not found".formatted(restaurantId.value())));

        loadDishByIdPort.findById(restaurantId, dishId)
                .orElseThrow(() -> new EntityNotFoundException("Dish {%s} not found".formatted(dishId.value())));

        Dish updatedDish = new Dish();
        updatedDish.setId(new Id<Dish>(cmd.id()));
        updatedDish.setName(cmd.name());
        updatedDish.setDishType(cmd.dishType());
        cmd.foodTags().forEach(updatedDish::addFoodTag);
        updatedDish.setDescription(cmd.description());
        updatedDish.setPrice(cmd.price());
        updatedDish.setPictureUrl(cmd.pictureUrl());

        editDishDraftPort.edit(restaurantId, updatedDish);

    }
}
