package com.omarhammad.kdg_backend.restaurants.adpaters.out;

import com.omarhammad.kdg_backend.common.Email;
import com.omarhammad.kdg_backend.restaurants.domain.Owner;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadOwnerData;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadRestaurantData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class inMemoryAdapter implements LoadRestaurantData, LoadOwnerData {

    private final List<Restaurant> restaurants;
    private final Logger logger;
    public List<Owner> owners;

    public inMemoryAdapter() {
        this.restaurants = new ArrayList<>();
        this.logger = LoggerFactory.getLogger(inMemoryAdapter.class);
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
        showRestaurants();
    }


    public void showRestaurants() {
        restaurants.forEach(restaurant -> logger.info("{}", restaurant));

    }

    @Override
    public Owner findOwnerById(Long ownerId) {
        return owners.stream().filter(owner -> owner.getId().equals(ownerId)).findFirst().get();
    }

}
