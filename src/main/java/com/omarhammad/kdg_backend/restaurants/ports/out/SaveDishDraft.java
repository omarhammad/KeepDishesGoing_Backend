package com.omarhammad.kdg_backend.restaurants.ports.out;

import com.omarhammad.kdg_backend.common.sharedDomain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.Dish;

public interface SaveDishDraft {

    void saveDishDraft(Id restaurantId, Dish dish);

}
