package com.omarhammad.kdg_backend.restaurants.core;

import com.omarhammad.kdg_backend.restaurants.domain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.Dish;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.EntityNotFoundException;
import com.omarhammad.kdg_backend.restaurants.ports.in.FindDishesByRestaurantIdUseCase;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadDishPort;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadRestaurantPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class DefaultFindDishesByRestaurantIdUseCase implements FindDishesByRestaurantIdUseCase {


    private LoadDishPort loadDishPort;
    private LoadRestaurantPort loadRestaurantPort;

    @Override
    public List<Dish> findDishesByRestaurantId(Id<Restaurant> restaurantId) {

        loadRestaurantPort.findRestaurantById(restaurantId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Restaurant {%s} not found".formatted(restaurantId.value())));

        List<Dish> dishes = loadDishPort.findDishesByRestaurantId(restaurantId);
        if (Objects.isNull(dishes)) {
            dishes = new ArrayList<>();
        }

        return dishes;
    }

}
