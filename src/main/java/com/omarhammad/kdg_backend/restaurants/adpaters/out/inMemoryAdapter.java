package com.omarhammad.kdg_backend.restaurants.adpaters.out;

import com.omarhammad.kdg_backend.common.sharedDomain.Email;
import com.omarhammad.kdg_backend.common.sharedDomain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.Dish;
import com.omarhammad.kdg_backend.restaurants.domain.Owner;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.ports.out.*;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class inMemoryAdapter implements
        SaveRestaurantData, LoadRestaurantsData, LoadOwnerData, SaveDishDraft, LoadRestaurantById {

    private final List<Restaurant> restaurants;
    private final List<Owner> owners;
    private final Map<Id, List<Dish>> dishesRestauarntMap;


    public inMemoryAdapter() {
        this.restaurants = new ArrayList<>();
        this.dishesRestauarntMap = new HashMap<>();
        this.owners = new ArrayList<>(List.of(new Owner(
                        new Id("59792e92-43a0-4a68-9ca3-b963359869cd"),
                        "Omar",
                        "Hammad",
                        new Email("omar.hammad@example.com"),
                        "+32470000001",
                        "omarh",
                        "securePass123"
                ), new Owner(
                        new Id("fa12fe09-3fd5-4ffb-a18f-019db62e7caf"),
                        "Ahmed",
                        "Ali",
                        new Email("haneen.ali@example.com"),
                        "+32470000002",
                        "ahmeda",
                        "mySecretPass456"

                ), new Owner(
                        new Id("1494f798-c30a-4a6d-ac7d-502ff746c9b5"),
                        "Sepp",
                        "Janssens",
                        new Email("sepp.janssens@example.com"),
                        "+32470000003",
                        "seppj",
                        "Pa$$word789"
                )

        ));
    }

    @Override
    public void saveRestaurant(Restaurant restaurant) {
        restaurant.setId(Id.createNewId());
        restaurants.add(restaurant);
    }


    @Override
    public Owner findOwnerById(Id ownerId) {

        return owners.stream().filter(owner -> owner.getId().equals(ownerId)).findFirst().get();
    }

    @Override
    public List<Restaurant> findAllRestaurants() {
        return this.restaurants;
    }

    @Override
    public void saveDishDraft(Id restaurantId, Dish dish) {
        dishesRestauarntMap.computeIfAbsent(restaurantId, id -> new ArrayList<>()).add(dish);
    }


    @Override
    public Restaurant findRestaurantBYId(Id restaurantId) {

        return restaurants.stream()
                .filter(restaurant -> restaurant.getId().equals(restaurantId))
                .findFirst()
                .orElse(null);
    }
}
