package com.omarhammad.kdg_backend.orders.ports.out;

import com.omarhammad.kdg_backend.orders.domain.DishProjection;

public interface EditDishProjectionPort {
    void edit(DishProjection dishProjection);
}
