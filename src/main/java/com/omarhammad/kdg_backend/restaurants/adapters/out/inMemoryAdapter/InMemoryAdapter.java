package com.omarhammad.kdg_backend.restaurants.adapters.out.inMemoryAdapter;

import com.omarhammad.kdg_backend.restaurants.domain.Email;
import com.omarhammad.kdg_backend.restaurants.domain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.Dish;
import com.omarhammad.kdg_backend.restaurants.domain.Owner;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.ports.out.*;
import lombok.extern.slf4j.Slf4j;

import java.util.*;


@Slf4j
public class InMemoryAdapter implements
        SaveRestaurantPort, LoadRestaurantPort, LoadOwnerPort,
        SaveDishDraftPort, EditDishPort,
        LoadDishPort {

    private final List<Restaurant> restaurants;
    private final List<Owner> owners;


    public InMemoryAdapter() {
        this.restaurants = new ArrayList<>();
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
        restaurants.stream()
                .filter(restaurant -> restaurant.getId().equals(restaurantId))
                .findFirst()
                .ifPresent(restaurant -> restaurant.addDish(dish));

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
                .filter((restaurant -> restaurant.getOwnerId().equals(ownerId)))
                .findFirst();
    }

    @Override
    public void edit(Id<Restaurant> restaurantId, Dish updatedDish) {

        restaurants.stream()
                .filter(restaurant -> restaurant.getId().equals(restaurantId))
                .findFirst()
                .ifPresent(restaurant -> {
                    List<Dish> dishes = restaurant.getDishes();
                    for (int i = 0; i < dishes.size(); i++) {
                        if (dishes.get(i).getId().equals(updatedDish.getId())) {
                            dishes.set(i, updatedDish);
                            log.info("Updated Dish: {}", dishes.get(i));
                            break;
                        }
                    }
                });

    }

    @Override
    public Optional<Dish> findById(Id<Restaurant> restaurantId, Id<Dish> dishId) {
        Optional<Restaurant> restaurant = restaurants.stream()
                .filter(r -> r.getId().equals(restaurantId))
                .findFirst();
        return restaurant.flatMap(r ->
                r.getDishes()
                        .stream()
                        .filter(dish -> dish.getId().equals(dishId))
                        .findFirst()
        );
    }

    @Override
    public List<Dish> findDishesByRestaurantId(Id<Restaurant> restaurantId) {
        Optional<Restaurant> restaurant = restaurants.stream()
                .filter(r -> r.getId().equals(restaurantId))
                .findFirst();

        if (restaurant.isPresent()) {
            return restaurant.get().getDishes();
        }
        return new ArrayList<>();
    }

}
