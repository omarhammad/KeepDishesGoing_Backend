package com.omarhammad.kdg_backend.restaurants.core;

import com.omarhammad.kdg_backend.restaurants.domain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.Owner;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.OwnerAlreadyHaveRestaurantException;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.EntityNotFoundException;
import com.omarhammad.kdg_backend.restaurants.ports.in.CreateRestaurantCmd;
import com.omarhammad.kdg_backend.restaurants.ports.in.CreateRestaurantUseCase;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadOwnerPort;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadRestaurantPort;
import com.omarhammad.kdg_backend.restaurants.ports.out.SaveRestaurantPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DefaultCreateRestaurantUseCase implements CreateRestaurantUseCase {


    private final SaveRestaurantPort saveRestaurantPort;
    private final LoadOwnerPort loadOwnerPort;
    private final LoadRestaurantPort loadRestaurantPort;

    @Override
    public void CreateRestaurant(CreateRestaurantCmd cmd) {

        Id<Owner> ownerId = new Id<>(cmd.ownerId());
        loadOwnerPort.findOwnerById(ownerId)
                .orElseThrow(() -> new EntityNotFoundException("Owner {%s} not found ".formatted(cmd.ownerId())));

        loadRestaurantPort.loadRestaurantByOwnerId(new Id<>(cmd.ownerId()))
                .ifPresent((r) -> {
                    throw new OwnerAlreadyHaveRestaurantException("Owner already has a (%s) restaurant".formatted(r.getName()));
                });

        Restaurant restaurant = new Restaurant();
        restaurant.setId(Id.<Restaurant>createNewId());
        restaurant.setName(cmd.name());
        restaurant.setEmail(cmd.email());
        restaurant.setAddress(cmd.address());
        restaurant.setResPictureUrl(cmd.resPictureUrl());
        cmd.dayOpeningHours().forEach(restaurant::addOpeningHoursForDay);
        restaurant.setCuisine(cmd.cuisine());
        restaurant.setDefaultPrepTime(cmd.defaultPrepTime());
        restaurant.setOwnerId(new Id<>(cmd.ownerId()));

        saveRestaurantPort.save(restaurant);


    }
}
