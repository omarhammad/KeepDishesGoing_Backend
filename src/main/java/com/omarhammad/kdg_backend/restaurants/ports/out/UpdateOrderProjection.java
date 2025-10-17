package com.omarhammad.kdg_backend.restaurants.ports.out;

import com.omarhammad.kdg_backend.restaurants.domain.OrderProjection;

public interface UpdateOrderProjection {

    void update(OrderProjection orderProjection);
}
