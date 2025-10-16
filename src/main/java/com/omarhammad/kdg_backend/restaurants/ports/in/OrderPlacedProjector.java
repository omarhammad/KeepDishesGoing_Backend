package com.omarhammad.kdg_backend.restaurants.ports.in;

import com.omarhammad.kdg_backend.restaurants.domain.IncomingOrder;

public interface OrderPlacedProjector {

    void project(OrderPlacedProjectionCmd cmd);

}
