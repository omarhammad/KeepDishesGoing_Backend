package com.omarhammad.kdg_backend.restaurants.adpaters.out;

import com.omarhammad.kdg_backend.common.sharedDomain.Email;
import com.omarhammad.kdg_backend.restaurants.domain.Owner;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadOwnerData;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadRestaurantsData;
import com.omarhammad.kdg_backend.restaurants.ports.out.SaveRestaurantData;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class inMemoryAdapter implements SaveRestaurantData, LoadRestaurantsData, LoadOwnerData {

    private final List<Restaurant> restaurants;
    public List<Owner> owners;

    public inMemoryAdapter() {
        this.restaurants = new ArrayList<>();
        this.owners = new ArrayList<>(List.of(new Owner(
                        1L,
                        "Omar",
                        "Hammad",
                        new Email("omar.hammad@example.com"),
                        "+32470000001",
                        "omarh",
                        "securePass123"
                ), new Owner(
                        2L,
                        "Ahmed",
                        "Ali",
                        new Email("haneen.ali@example.com"),
                        "+32470000002",
                        "ahmeda",
                        "mySecretPass456"

                ), new Owner(
                        3L,
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
        restaurant.setId(UUID.randomUUID());
        restaurants.add(restaurant);
    }


    @Override
    public Owner findOwnerById(Long ownerId) {
        return owners.stream().filter(owner -> owner.getId().equals(ownerId)).findFirst().get();
    }

    @Override
    public List<Restaurant> findAllRestaurants() {
        return this.restaurants;
    }
}
