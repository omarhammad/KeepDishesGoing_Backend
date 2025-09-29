package com.omarhammad.kdg_backend.restaurants.core;

import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.ports.in.FindAllRestaurantsUseCase;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadRestaurantsPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class DefaultFindAllRestaurantsUseCase implements FindAllRestaurantsUseCase {

    private final LoadRestaurantsPort loadRestaurantsPort;

    @Override
    public List<Restaurant> findAllRestaurants() {
        return loadRestaurantsPort.findAllRestaurants();
    }
}
