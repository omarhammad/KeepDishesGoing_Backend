package com.omarhammad.kdg_backend.orders.ports.in;


public interface OrderStatusProjector {
    void project(OrderStatusProjectorCmd cmd);

}
