package com.omarhammad.kdg_backend.restaurants.core;

import com.omarhammad.kdg_backend.restaurants.domain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.Owner;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.EntityNotFoundException;
import com.omarhammad.kdg_backend.restaurants.ports.in.FindAllRestaurantsUseCase;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadOwnerPort;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadRestaurantPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DefaultFindAllRestaurantsUseCase implements FindAllRestaurantsUseCase {

    private final LoadRestaurantPort loadRestaurantPort;
    private final LoadOwnerPort loadOwnerPort;

    @Override
    public List<Restaurant> findAllRestaurants() {
       return loadRestaurantPort.findAllRestaurants();

    }
}
