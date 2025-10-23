package com.omarhammad.kdg_backend.restaurants.ports.in;


public interface OrderDeliveredProjector {

    void project(OrderDeliveredProjectorCmd cmd);
}
