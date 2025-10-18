package com.omarhammad.kdg_backend.restaurants.core;

import com.omarhammad.kdg_backend.restaurants.domain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.Dish;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.EntityNotFoundException;
import com.omarhammad.kdg_backend.restaurants.ports.in.FindDishesByRestaurantIdUseCase;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadRestaurantPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DefaultFindDishesByRestaurantIdUseCase implements FindDishesByRestaurantIdUseCase {


    private LoadRestaurantPort loadRestaurantPort;

    @Override
    public List<Dish> findDishesByRestaurantId(Id<Restaurant> restaurantId) {

       Restaurant restaurant =  loadRestaurantPort.findRestaurantById(restaurantId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Restaurant {%s} not found".formatted(restaurantId.value())));

        return restaurant.findAllDishes();
    }

}
