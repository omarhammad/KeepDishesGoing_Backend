package com.omarhammad.kdg_backend.orders.ports.out;

import com.omarhammad.kdg_backend.orders.domain.DishProjection;

import java.util.Optional;

public interface SaveDishProjectionPort {
    DishProjection save(DishProjection dishProjection);
}
