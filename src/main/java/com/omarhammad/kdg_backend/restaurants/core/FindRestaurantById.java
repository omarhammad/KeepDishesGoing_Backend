package com.omarhammad.kdg_backend.restaurants.core;

import com.omarhammad.kdg_backend.common.sharedDomain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.EntityNotFoundException;
import com.omarhammad.kdg_backend.restaurants.ports.in.IFindRestaurantById;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadRestaurantById;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class FindRestaurantById implements IFindRestaurantById {

    LoadRestaurantById loadRestaurantById;

    @Override
    public Restaurant findRestaurantById(Id restaurantId) throws EntityNotFoundException {

        Restaurant restaurant = loadRestaurantById.findRestaurantBYId(restaurantId);
        if (Objects.isNull(restaurant)) {
            throw new EntityNotFoundException("Restaurant {%s} not found".formatted(restaurantId.value()));
        }
        return restaurant;
    }

}
