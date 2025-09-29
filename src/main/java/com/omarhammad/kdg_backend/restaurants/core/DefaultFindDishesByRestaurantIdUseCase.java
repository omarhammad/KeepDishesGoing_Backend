package com.omarhammad.kdg_backend.restaurants.core;

import com.omarhammad.kdg_backend.common.sharedDomain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.Dish;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.EntityNotFoundException;
import com.omarhammad.kdg_backend.restaurants.ports.in.FindDishesByRestaurantIdUseCase;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadDishesByRestaurantIdPort;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadRestaurantByIdPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DefaultFindDishesByRestaurantIdUseCase implements FindDishesByRestaurantIdUseCase {


    private LoadDishesByRestaurantIdPort loadDishesByRestaurantIdPort;
    private LoadRestaurantByIdPort loadRestaurantByIdPort;

    @Override
    public List<Dish> findDishesByRestaurantId(Id<Restaurant> restaurantId) {

        loadRestaurantByIdPort.findRestaurantById(restaurantId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Restaurant {%s} not found".formatted(restaurantId.value())));

        return loadDishesByRestaurantIdPort.findDishesByRestaurantId(restaurantId).orElse(new ArrayList<>());
    }

}
