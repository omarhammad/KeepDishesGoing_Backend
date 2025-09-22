package com.omarhammad.kdg_backend.restaurants.core;

import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.ports.in.IFindAllRestaurants;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadRestaurantsData;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FindAllRestaurants implements IFindAllRestaurants {

    private final LoadRestaurantsData loadRestaurantsData;

    public FindAllRestaurants(LoadRestaurantsData loadRestaurantsData) {
        this.loadRestaurantsData = loadRestaurantsData;
    }

    @Override
    public List<Restaurant> findAllRestaurants() {
        return loadRestaurantsData.findAllRestaurants();
    }
}
