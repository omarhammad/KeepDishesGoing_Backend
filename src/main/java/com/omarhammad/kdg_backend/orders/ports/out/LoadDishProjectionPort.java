package com.omarhammad.kdg_backend.orders.ports.out;

import com.omarhammad.kdg_backend.orders.domain.DishProjection;
import com.omarhammad.kdg_backend.orders.domain.Id;

import java.util.Optional;

public interface LoadDishProjectionPort {

    Optional<DishProjection> findById(Id<DishProjection> dishProjectionId);
}
