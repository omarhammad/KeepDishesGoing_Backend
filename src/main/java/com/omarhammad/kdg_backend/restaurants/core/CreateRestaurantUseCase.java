package com.omarhammad.kdg_backend.restaurants.core;

import com.omarhammad.kdg_backend.common.sharedDomain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.ports.in.CreateRestaurantCmd;
import com.omarhammad.kdg_backend.restaurants.ports.in.ICreateRestaurantUseCase;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadOwnerData;
import com.omarhammad.kdg_backend.restaurants.ports.out.SaveRestaurantData;
import org.springframework.stereotype.Service;

@Service
public class CreateRestaurantUseCase implements ICreateRestaurantUseCase {


    private final SaveRestaurantData saveRestaurantData;
    private final LoadOwnerData loadOwnerData;


    public CreateRestaurantUseCase(SaveRestaurantData saveRestaurantData, LoadOwnerData loadOwnerData) {
        this.saveRestaurantData = saveRestaurantData;
        this.loadOwnerData = loadOwnerData;
    }



    @Override
    public void CreateRestaurant(CreateRestaurantCmd cmd) {

        Restaurant restaurant = new Restaurant();
        restaurant.setName(cmd.name());
        restaurant.setEmail(cmd.email());
        restaurant.setAddress(cmd.address());
        restaurant.setResPictureUrl(cmd.resPictureUrl());
        cmd.dayOpeningHours().forEach(restaurant::addOpeningHoursForDay);
        restaurant.setCuisine(cmd.cuisine());
        restaurant.setDefaultPrepTime(cmd.defaultPrepTime());
        Id ownerId = new Id(cmd.ownerId());
        restaurant.setOwner(loadOwnerData.findOwnerById(ownerId));

        saveRestaurantData.saveRestaurant(restaurant);


    }
}
