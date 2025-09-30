package com.omarhammad.kdg_backend.restaurants.core;

import com.omarhammad.kdg_backend.restaurants.domain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.Dish;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.EntityNotFoundException;
import com.omarhammad.kdg_backend.restaurants.ports.in.CreateDishDraftCmd;
import com.omarhammad.kdg_backend.restaurants.ports.in.CreateDishDraftUseCase;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadRestaurantByIdPort;
import com.omarhammad.kdg_backend.restaurants.ports.out.SaveDishDraftPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DefaultCreateDishDraftUseCase implements CreateDishDraftUseCase {


    private final SaveDishDraftPort saveDishDraftPort;
    private final LoadRestaurantByIdPort loadRestaurantByIdPort;

    @Override
    public void createDish(Id<Restaurant> restaurantId, CreateDishDraftCmd cmd) throws EntityNotFoundException {

        loadRestaurantByIdPort.findRestaurantById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant {%s} not found".formatted(restaurantId.value())));

        Dish dish = new Dish();
        dish.setId(Id.<Dish>createNewId());
        dish.setName(cmd.name());
        dish.setDishType(cmd.dishType());
        cmd.foodTags().forEach(dish::addFoodTag);
        dish.setDescription(cmd.description());
        dish.setPrice(cmd.price());
        dish.setPictureUrl(cmd.pictureUrl());

        saveDishDraftPort.save(restaurantId, dish);

    }
}
