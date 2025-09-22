package com.omarhammad.kdg_backend.restaurants.core;

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


    /*        http POST :8080/api/restaurants   name="La Piazza"   email="contact@lapiazza.com"   resPictureUrl="https://example.com/images/lapiazza.jpg"   cuisine="ITALIAN"   defaultPrepTime:=30   ownerId:=1   address:='{
                "street": "Main Street",
                "number": "42",
                "postalCode": "2000",
                "city": "Antwerp",
                "country": "Belgium"
    }'   dayOpeningHours:='{
            "MONDAY": { "open": "11:00", "close": "22:00" },
            "TUESDAY": { "open": "11:00", "close": "22:00" },
            "WEDNESDAY": { "open": "11:00", "close": "22:00" },
            "THURSDAY": { "open": "11:00", "close": "22:00" },
            "FRIDAY": { "open": "11:00", "close": "23:00" },
            "SATURDAY": { "open": "12:00", "close": "23:00" },
            "SUNDAY": { "open": "12:00", "close": "21:00" }
            }'*/
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
        restaurant.setOwner(loadOwnerData.findOwnerById(cmd.ownerId()));

        saveRestaurantData.saveRestaurant(restaurant);


    }
}
