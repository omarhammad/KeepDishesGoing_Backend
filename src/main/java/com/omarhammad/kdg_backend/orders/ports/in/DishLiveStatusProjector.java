package com.omarhammad.kdg_backend.orders.ports.in;

public interface DishLiveStatusProjector {

    void project(DishLiveStatusProjectorCmd cmd);
}
