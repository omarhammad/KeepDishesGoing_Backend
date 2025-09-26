package com.omarhammad.kdg_backend.restaurants.ports.in;

import com.omarhammad.kdg_backend.common.sharedDomain.Id;

public interface ICreateRestaurantUseCase {

    void CreateRestaurant(CreateRestaurantCmd cmd);
}
