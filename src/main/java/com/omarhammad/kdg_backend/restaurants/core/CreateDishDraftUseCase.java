package com.omarhammad.kdg_backend.restaurants.core;

import com.omarhammad.kdg_backend.common.sharedDomain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.Dish;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.EntityNotFoundException;
import com.omarhammad.kdg_backend.restaurants.ports.in.CreateDishDraftCmd;
import com.omarhammad.kdg_backend.restaurants.ports.in.ICreateDishDraftUseCase;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadRestaurantById;
import com.omarhammad.kdg_backend.restaurants.ports.out.SaveDishDraft;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class CreateDishDraftUseCase implements ICreateDishDraftUseCase {


    private final SaveDishDraft saveDishDraft;
    private final LoadRestaurantById loadRestaurantById;

    @Override
    public void createDish(Id restaurantId, CreateDishDraftCmd cmd) throws EntityNotFoundException {

        Restaurant restaurant = loadRestaurantById.findRestaurantBYId(restaurantId);
        if (Objects.isNull(restaurant)) {
            throw new EntityNotFoundException("Restaurant {%s} not found".formatted(restaurantId.value()));
        }

        Dish dish = new Dish();
        dish.setName(cmd.name());
        dish.setDishType(cmd.dishType());
        cmd.foodTags().forEach(dish::addFoodTag);
        dish.setDescription(cmd.description());
        dish.setPrice(cmd.price());
        dish.setPictureUrl(cmd.pictureUrl());

        saveDishDraft.saveDishDraft(restaurantId, dish);

    }
}
