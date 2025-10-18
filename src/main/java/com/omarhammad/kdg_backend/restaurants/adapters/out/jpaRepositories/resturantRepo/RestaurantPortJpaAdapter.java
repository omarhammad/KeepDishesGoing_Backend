package com.omarhammad.kdg_backend.restaurants.adapters.out.jpaRepositories.resturantRepo;

import com.omarhammad.kdg_backend.restaurants.adapters.out.jpaRepositories.resturantRepo.entities.DishDraftJpa;
import com.omarhammad.kdg_backend.restaurants.adapters.out.jpaRepositories.resturantRepo.entities.DishJpaEntity;
import com.omarhammad.kdg_backend.restaurants.adapters.out.jpaRepositories.resturantRepo.entities.DishLiveJpa;
import com.omarhammad.kdg_backend.restaurants.adapters.out.jpaRepositories.resturantRepo.entities.AddressJpa;
import com.omarhammad.kdg_backend.restaurants.adapters.out.jpaRepositories.resturantRepo.entities.OpeningHoursJpa;
import com.omarhammad.kdg_backend.restaurants.adapters.out.jpaRepositories.resturantRepo.entities.RestaurantsJpaEntity;
import com.omarhammad.kdg_backend.restaurants.domain.*;
import com.omarhammad.kdg_backend.restaurants.domain.enums.*;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.EntityNotFoundException;
import com.omarhammad.kdg_backend.restaurants.ports.out.EditRestaurantPort;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadRestaurantPort;
import com.omarhammad.kdg_backend.restaurants.ports.out.SaveRestaurantPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Repository
public class RestaurantPortJpaAdapter implements SaveRestaurantPort, EditRestaurantPort, LoadRestaurantPort {

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
    public Optional<Restaurant> findRestaurantByOwnerId(Id<Owner> ownerId) {

        Optional<RestaurantsJpaEntity> restaurantsJpaEntity =
                repository.findRestaurantsJpaEntitiesByOwner(UUID.fromString(ownerId.value()));

        return restaurantsJpaEntity.map(this::toRestaurant);


    }

    @Override
    public void edit(Restaurant restaurant) {

        UUID restaurantId = UUID.fromString(restaurant.getId().value());
        RestaurantsJpaEntity currentRestaurantsJpaEntity = repository
                .findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant {%s} not found".formatted(restaurantId)));

        RestaurantsJpaEntity updatedRestaurantsJpaEntity = toJpaEntity(restaurant);

        currentRestaurantsJpaEntity.setName(updatedRestaurantsJpaEntity.getName());
        currentRestaurantsJpaEntity.setEmail(updatedRestaurantsJpaEntity.getEmail());
        currentRestaurantsJpaEntity.setAddress(updatedRestaurantsJpaEntity.getAddress());
        currentRestaurantsJpaEntity.setResPictureUrl(updatedRestaurantsJpaEntity.getResPictureUrl());
        currentRestaurantsJpaEntity.setManualOpening(updatedRestaurantsJpaEntity.getManualOpening());
        currentRestaurantsJpaEntity.setCuisine(updatedRestaurantsJpaEntity.getCuisine());
        currentRestaurantsJpaEntity.setDefaultPrepTime(updatedRestaurantsJpaEntity.getDefaultPrepTime());
        currentRestaurantsJpaEntity.setHasScheduledPublish(updatedRestaurantsJpaEntity.isHasScheduledPublish());
        currentRestaurantsJpaEntity.setOwner(updatedRestaurantsJpaEntity.getOwner());
        updatedRestaurantsJpaEntity.getDayOpeningHours().forEach(currentRestaurantsJpaEntity::addDayOpeningHours);


        for (DishJpaEntity updatedDish : updatedRestaurantsJpaEntity.getDishJpaEntities()) {
            currentRestaurantsJpaEntity.getDishJpaEntities().stream()
                    .filter(currentDish -> currentDish.getId().equals(updatedDish.getId()))
                    .findFirst()
                    .ifPresentOrElse(
                            existingDish -> editDish(updatedDish, existingDish), // update existing
                            () -> currentRestaurantsJpaEntity.addDishJpaEntity(updatedDish) // add new
                    );
        }


        repository.save(currentRestaurantsJpaEntity);

    }


    private void editDish(DishJpaEntity udaptedDishJpaEntity, DishJpaEntity existingJpaDishEntity) {

        existingJpaDishEntity.setInStock(udaptedDishJpaEntity.isInStock());
        existingJpaDishEntity.setScheduledTime(udaptedDishJpaEntity.getScheduledTime());
        existingJpaDishEntity.setLive(udaptedDishJpaEntity.getLive());
        existingJpaDishEntity.setDraft(udaptedDishJpaEntity.getDraft());
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

        restaurant.findAllDishes()
                .forEach(dish -> restaurantsJpaEntity.addDishJpaEntity(toDishJpaEntity(dish)));

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

        restaurantsJpaEntity.getDishJpaEntities()
                .forEach(dishJpaEntity -> restaurant.addDish(toDish(dishJpaEntity)));

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


    //    Dish part
    private DishJpaEntity toDishJpaEntity(Dish dish) {

        DishJpaEntity dishJpaEntity = new DishJpaEntity(
                UUID.fromString(dish.getId().value()),
                dish.isInStock(),
                dish.getScheduledTime()
        );

        dishJpaEntity.setDraft(toDishDraftJpa(dish.getDraft()));
        dishJpaEntity.setLive(toDishLiveJpa(dish.getLive()));

        return dishJpaEntity;
    }


    private DishLiveJpa toDishLiveJpa(DishData live) {
        if (Objects.isNull(live)) return null;
        DishLiveJpa dishLiveJpa =
                new DishLiveJpa(
                        live.name(),
                        live.dishType().name(),
                        live.description(),
                        live.price(),
                        live.pictureUrl()
                );
        live.foodTags().forEach(foodTag -> dishLiveJpa.addFoodTags(foodTag.name()));
        return dishLiveJpa;
    }

    private DishDraftJpa toDishDraftJpa(DishData draft) {
        if (Objects.isNull(draft)) return null;
        DishDraftJpa dishDraftJpa =
                new DishDraftJpa(
                        draft.name(),
                        draft.dishType().name(),
                        draft.description(),
                        draft.price(),
                        draft.pictureUrl()
                );
        draft.foodTags().forEach(foodTag -> dishDraftJpa.addFoodTags(foodTag.name()));
        return dishDraftJpa;
    }

    private Dish toDish(DishJpaEntity dishJpaEntity) {

        return new Dish(
                new Id<>(dishJpaEntity.getId().toString()),
                dishJpaEntity.isInStock(),
                dishJpaEntity.getScheduledTime(),
                toDishDataLive(dishJpaEntity.getLive()),
                toDishDataDraft(dishJpaEntity.getDraft())
        );

    }

    private DishData toDishDataLive(DishLiveJpa liveJpa) {
        if (Objects.isNull(liveJpa)) return null;
        DishData liveData = new DishData(
                liveJpa.getName(),
                DishType.valueOf(liveJpa.getDishType()),
                liveJpa.getDescription(),
                liveJpa.getPrice(),
                liveJpa.getPictureUrl()
        );
        liveJpa.getFoodTags().forEach(foodTagS -> liveData.addFoodTag(FoodTag.valueOf(foodTagS)));
        return liveData;
    }

    private DishData toDishDataDraft(DishDraftJpa draftJpa) {
        if (Objects.isNull(draftJpa)) return null;
        DishData draftData = new DishData(
                draftJpa.getName(),
                DishType.valueOf(draftJpa.getDishType()),
                draftJpa.getDescription(),
                draftJpa.getPrice(),
                draftJpa.getPictureUrl()
        );

        draftJpa.getFoodTags().forEach(foodTagS -> draftData.addFoodTag(FoodTag.valueOf(foodTagS)));
        return draftData;
    }

}


