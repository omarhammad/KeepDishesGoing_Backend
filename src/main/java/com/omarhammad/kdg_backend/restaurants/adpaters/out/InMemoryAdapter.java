package com.omarhammad.kdg_backend.restaurants.adpaters.out;

import com.omarhammad.kdg_backend.restaurants.domain.Email;
import com.omarhammad.kdg_backend.restaurants.domain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.Dish;
import com.omarhammad.kdg_backend.restaurants.domain.Owner;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.ports.out.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Slf4j
public class InMemoryAdapter implements
        SaveRestaurantPort, LoadRestaurantsPort, LoadOwnerPort,
        SaveDishDraftPort, EditDishDraftPort, LoadDishesByRestaurantIdPort,
        LoadDishByIdPort, LoadRestaurantByIdPort, LoadRestaurantByOwnerIdPort {

    private final List<Restaurant> restaurants;
    private final List<Owner> owners;
    private final Map<Id<Restaurant>, List<Dish>> dishesRestauarntMap;


    public InMemoryAdapter() {
        this.restaurants = new ArrayList<>();
        this.dishesRestauarntMap = new HashMap<>();
        this.owners = new ArrayList<>(List.of(new Owner(
                        new Id<Owner>("59792e92-43a0-4a68-9ca3-b963359869cd"),
                        "Omar",
                        "Hammad",
                        new Email("omar.hammad@example.com"),
                        "+32470000001",
                        "omarh",
                        "securePass123"
                ), new Owner(
                        new Id<Owner>("fa12fe09-3fd5-4ffb-a18f-019db62e7caf"),
                        "Ahmed",
                        "Ali",
                        new Email("haneen.ali@example.com"),
                        "+32470000002",
                        "ahmeda",
                        "mySecretPass456"

                ), new Owner(
                        new Id<Owner>("1494f798-c30a-4a6d-ac7d-502ff746c9b5"),
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
    public void save(Restaurant restaurant) {
        restaurants.add(restaurant);
    }


    @Override
    public Optional<Owner> findOwnerById(Id<Owner> ownerId) {

        return owners.stream().filter(owner -> owner.getId().equals(ownerId)).findFirst();
    }

    @Override
    public List<Restaurant> findAllRestaurants() {
        return this.restaurants;
    }

    @Override
    public void save(Id<Restaurant> restaurantId, Dish dish) {
        log.info("New Dish Id: {}", dish.getId().value());
        dishesRestauarntMap.computeIfAbsent(restaurantId, id -> new ArrayList<>()).add(dish);
    }


    @Override
    public Optional<Restaurant> findRestaurantById(Id<Restaurant> restaurantId) {

        return restaurants.stream()
                .filter(restaurant -> restaurant.getId().equals(restaurantId))
                .findFirst();
    }

    @Override
    public Optional<Restaurant> loadRestaurantByOwnerId(Id<Owner> ownerId) {
        return restaurants.stream()
                .filter((restaurant -> restaurant.getOwner().getId().equals(ownerId)))
                .findFirst();
    }

    @Override
    public void edit(Id<Restaurant> restaurantId, Dish updatedDish) {
        List<Dish> dishes = dishesRestauarntMap.get(restaurantId);
        for (int i = 0; i < dishes.size(); i++) {
            if (dishes.get(i).getId().equals(updatedDish.getId())) {
                dishes.set(i, updatedDish);
                break;
            }
        }
    }

    @Override
    public Optional<Dish> findById(Id<Restaurant> restaurantId, Id<Dish> dishId) {
        return dishesRestauarntMap.get(restaurantId)
                .stream()
                .filter((dish -> dish.getId().equals(dishId)))
                .findFirst();
    }

    @Override
    public List<Dish> findDishesByRestaurantId(Id<Restaurant> restaurantId) {
        return dishesRestauarntMap.get(restaurantId);
    }
}
