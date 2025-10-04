package com.omarhammad.kdg_backend.restaurants.core;

import com.omarhammad.kdg_backend.restaurants.domain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.Dish;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.EntityNotFoundException;
import com.omarhammad.kdg_backend.restaurants.ports.in.EditDishDraftCmd;
import com.omarhammad.kdg_backend.restaurants.ports.in.EditDishDraftUseCase;
import com.omarhammad.kdg_backend.restaurants.ports.out.EditDishPort;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadDishPort;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadRestaurantPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DefaultEditDishDraftUseCase implements EditDishDraftUseCase {

    private final EditDishPort editDishPort;
    private final LoadRestaurantPort loadRestaurantPort;
    private final LoadDishPort loadDishPort;

    @Override
    public void editDishDraft(Id<Restaurant> restaurantId, Id<Dish> dishId, EditDishDraftCmd cmd) {


        loadRestaurantPort.findRestaurantById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant {%s} not found".formatted(restaurantId.value())));

        Dish dish = loadDishPort.findById(restaurantId, dishId)
                .orElseThrow(() -> new EntityNotFoundException("Dish {%s} not found".formatted(dishId.value())));


        dish.saveDraft(
                cmd.name(),
                cmd.dishType(),
                cmd.foodTags(),
                cmd.description(),
                cmd.price(),
                cmd.pictureUrl()
        );


        editDishPort.edit(restaurantId, dish);

    }
}
