package com.omarhammad.kdg_backend.restaurants.ports.out;

import com.omarhammad.kdg_backend.common.sharedDomain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;

public interface LoadRestaurantById {

    Restaurant findRestaurantBYId(Id restaurantId);

}
