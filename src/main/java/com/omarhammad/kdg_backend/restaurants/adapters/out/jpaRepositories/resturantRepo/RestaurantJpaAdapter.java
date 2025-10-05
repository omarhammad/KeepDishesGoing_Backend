package com.omarhammad.kdg_backend.restaurants.adapters.out.jpaRepositories.resturantRepo;

import com.omarhammad.kdg_backend.restaurants.adapters.out.jpaRepositories.resturantRepo.entities.AddressJpa;
import com.omarhammad.kdg_backend.restaurants.adapters.out.jpaRepositories.resturantRepo.entities.OpeningHoursJpa;
import com.omarhammad.kdg_backend.restaurants.adapters.out.jpaRepositories.resturantRepo.entities.RestaurantsJpaEntity;
import com.omarhammad.kdg_backend.restaurants.domain.*;
import com.omarhammad.kdg_backend.restaurants.domain.enums.Cuisine;
import com.omarhammad.kdg_backend.restaurants.domain.enums.Day;
import com.omarhammad.kdg_backend.restaurants.domain.enums.OpeningStatus;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.EntityNotFoundException;
import com.omarhammad.kdg_backend.restaurants.ports.out.EditRestaurant;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadRestaurantPort;
import com.omarhammad.kdg_backend.restaurants.ports.out.SaveRestaurantPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Repository
public class RestaurantJpaAdapter implements SaveRestaurantPort, EditRestaurant, LoadRestaurantPort {

    private RestaurantsRepository repository;

    @Override
    public void save(Restaurant restaurant) {

        RestaurantsJpaEntity restaurantsJpaEntity = toJpaEntity(restaurant);
        repository.save(restaurantsJpaEntity);

    }

    @Override
    public List<Restaurant> findAllRestaurants() {

        return repository.findAll().stream().map(this::toRestaurant).toList();
    }

    @Override
    public Optional<Restaurant> findRestaurantById(Id<Restaurant> restaurantId) {
        return repository.findById(UUID.fromString(restaurantId.value())).map(this::toRestaurant);

    }

    @Override
    public Optional<Restaurant> loadRestaurantByOwnerId(Id<Owner> ownerId) {

        Optional<RestaurantsJpaEntity> restaurantsJpaEntity =
                repository.findRestaurantsJpaEntitiesByOwner(UUID.fromString(ownerId.value()));

        return restaurantsJpaEntity.map(this::toRestaurant);


    }

    @Override
    public void edit(Restaurant restaurant) {

        UUID restaurantId = UUID.fromString(restaurant.getId().value());
        RestaurantsJpaEntity restaurantsJpaEntity = repository
                .findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant {%s} not found".formatted(restaurantId)));

        RestaurantsJpaEntity updatedRestaurantsJpaEntity = toJpaEntity(restaurant);

        restaurantsJpaEntity.setName(updatedRestaurantsJpaEntity.getName());
        restaurantsJpaEntity.setEmail(updatedRestaurantsJpaEntity.getEmail());
        restaurantsJpaEntity.setAddress(updatedRestaurantsJpaEntity.getAddress());
        restaurantsJpaEntity.setResPictureUrl(updatedRestaurantsJpaEntity.getResPictureUrl());
        restaurantsJpaEntity.setManualOpening(updatedRestaurantsJpaEntity.getManualOpening());
        restaurantsJpaEntity.setCuisine(updatedRestaurantsJpaEntity.getCuisine());
        restaurantsJpaEntity.setDefaultPrepTime(updatedRestaurantsJpaEntity.getDefaultPrepTime());
        restaurantsJpaEntity.setHasScheduledPublish(updatedRestaurantsJpaEntity.isHasScheduledPublish());
        restaurantsJpaEntity.setOwner(updatedRestaurantsJpaEntity.getOwner());
        updatedRestaurantsJpaEntity.getDayOpeningHours().forEach(restaurantsJpaEntity::addDayOpeningHours);
        repository.save(restaurantsJpaEntity);

    }

    private RestaurantsJpaEntity toJpaEntity(Restaurant restaurant) {
        RestaurantsJpaEntity restaurantsJpaEntity = new RestaurantsJpaEntity(
                UUID.fromString(restaurant.getId().value()),
                restaurant.getName(),
                restaurant.getEmail().email(),
                toAddressJpa(restaurant.getAddress()),
                restaurant.getResPictureUrl(),
                restaurant.getManualOpening().toString(),
                restaurant.getCuisine().toString(),
                restaurant.getDefaultPrepTime(),
                restaurant.hasScheduledPublish(),
                UUID.fromString(restaurant.getOwnerId().value())
        );


        restaurant.getDayOpeningHours().forEach((day, oh) -> {
            restaurantsJpaEntity.addDayOpeningHours(day.getValue(), new OpeningHoursJpa(oh.open(), oh.close()));
        });

        return restaurantsJpaEntity;
    }

    private AddressJpa toAddressJpa(Address address) {
        return new AddressJpa(
                address.street(),
                address.number(),
                address.postalCode(),
                address.city(),
                address.country()
        );
    }

    private Restaurant toRestaurant(RestaurantsJpaEntity restaurantsJpaEntity) {

        Restaurant restaurant = new Restaurant(
                new Id<>(restaurantsJpaEntity.getId().toString()),
                restaurantsJpaEntity.getName(),
                new Email(restaurantsJpaEntity.getEmail()),
                toAddress(restaurantsJpaEntity.getAddress()),
                restaurantsJpaEntity.getResPictureUrl(),
                OpeningStatus.valueOf(restaurantsJpaEntity.getManualOpening().toUpperCase()),
                Cuisine.valueOf(restaurantsJpaEntity.getCuisine().toUpperCase()),
                restaurantsJpaEntity.getDefaultPrepTime(),
                restaurantsJpaEntity.isHasScheduledPublish(),
                new Id<>(restaurantsJpaEntity.getOwner().toString())
        );


        restaurantsJpaEntity.getDayOpeningHours()
                .forEach((s, openingHoursJpa) ->
                        restaurant.addOpeningHoursForDay(Day.valueOf(s.toUpperCase()),
                                new OpeningHours(openingHoursJpa.open(), openingHoursJpa.close())));


        return restaurant;
    }

    private Address toAddress(AddressJpa addressJpa) {
        return new Address(
                addressJpa.street(),
                addressJpa.number(),
                addressJpa.postalCode(),
                addressJpa.city(),
                addressJpa.country()
        );
    }


}
