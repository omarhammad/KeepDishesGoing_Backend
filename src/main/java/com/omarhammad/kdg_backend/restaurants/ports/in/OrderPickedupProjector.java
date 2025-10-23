package com.omarhammad.kdg_backend.restaurants.ports.in;

public interface OrderPickedupProjector {


    void project(OrderPickedupProjectorCmd cmd);
}
