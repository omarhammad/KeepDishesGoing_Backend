package com.omarhammad.kdg_backend.restaurants.ports.in;

public interface OrderPlacedProjector {

    void project(OrderPlacedProjectionCmd cmd);

}
