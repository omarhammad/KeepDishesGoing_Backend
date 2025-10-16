package com.omarhammad.kdg_backend.restaurants.core;

import com.omarhammad.kdg_backend.restaurants.domain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.Owner;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.EntityNotFoundException;
import com.omarhammad.kdg_backend.restaurants.ports.in.FindRestaurantByOwnerIdUseCase;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadOwnerPort;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadRestaurantPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DefaultFindRestaurantByOwnerIdUseCase implements FindRestaurantByOwnerIdUseCase {

    private LoadRestaurantPort loadRestaurantPort;

    private LoadOwnerPort loadOwnerPort;

    @Override
    public Restaurant findRestaurantByOwnerId(Id<Owner> ownerId) {

        loadOwnerPort.findOwnerById(ownerId)
                .orElseThrow(() -> new EntityNotFoundException("Owner {%s} not found".formatted(ownerId.value())));

        return loadRestaurantPort.findRestaurantByOwnerId(ownerId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found"));

    }
}
