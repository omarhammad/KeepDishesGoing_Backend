package com.omarhammad.kdg_backend.restaurants.ports.out;

import com.omarhammad.kdg_backend.restaurants.domain.OrderProjection;

import java.util.Optional;

public interface SaveOrderProjection {

    Optional<OrderProjection> save(OrderProjection orderProjection);


}
