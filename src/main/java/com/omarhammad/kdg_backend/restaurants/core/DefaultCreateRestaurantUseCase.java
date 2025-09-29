package com.omarhammad.kdg_backend.restaurants.core;

import com.omarhammad.kdg_backend.common.sharedDomain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.Owner;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.BusinessRuleViolationException;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.EntityNotFoundException;
import com.omarhammad.kdg_backend.restaurants.ports.in.CreateRestaurantCmd;
import com.omarhammad.kdg_backend.restaurants.ports.in.CreateRestaurantUseCase;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadOwnerPort;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadRestaurantByOwnerIdPort;
import com.omarhammad.kdg_backend.restaurants.ports.out.SaveRestaurantPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DefaultCreateRestaurantUseCase implements CreateRestaurantUseCase {


    private final SaveRestaurantPort saveRestaurantPort;
    private final LoadOwnerPort loadOwnerPort;
    private final LoadRestaurantByOwnerIdPort loadRestaurantByOwnerIdPort;

    @Override
    public void CreateRestaurant(CreateRestaurantCmd cmd) {

        Restaurant restaurant = new Restaurant();
        restaurant.setId(Id.<Restaurant>createNewId());
        restaurant.setName(cmd.name());
        restaurant.setEmail(cmd.email());
        restaurant.setAddress(cmd.address());
        restaurant.setResPictureUrl(cmd.resPictureUrl());
        cmd.dayOpeningHours().forEach(restaurant::addOpeningHoursForDay);
        restaurant.setCuisine(cmd.cuisine());
        restaurant.setDefaultPrepTime(cmd.defaultPrepTime());

        Id<Owner> ownerId = new Id<>(cmd.ownerId());
        Owner owner = loadOwnerPort.findOwnerById(ownerId)
                .orElseThrow(() -> new EntityNotFoundException("Owner {%s} not found ".formatted(cmd.ownerId())));

        loadRestaurantByOwnerIdPort.loadRestaurantByOwnerId(owner.getId())
                .ifPresent((r) -> {
                    throw new BusinessRuleViolationException("Owner already has a (%s) restaurant".formatted(r.getName()));
                });


        restaurant.setOwner(owner);

        saveRestaurantPort.save(restaurant);


    }
}
