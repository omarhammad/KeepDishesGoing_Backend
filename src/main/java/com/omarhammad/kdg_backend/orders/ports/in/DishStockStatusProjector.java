package com.omarhammad.kdg_backend.orders.ports.in;

public interface DishStockStatusProjector {

    void project(DishStockStatusProjectorCmd cmd);
}
