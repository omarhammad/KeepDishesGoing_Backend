package com.omarhammad.kdg_backend.orders.ports.in;

import com.omarhammad.kdg_backend.orders.domain.Id;
import com.omarhammad.kdg_backend.orders.domain.Order;

import java.util.List;

public interface FindProcessedOrdersByRestaurantId {

    List<Order> findProcessedOrdersByRestaurantId(Id restaurantId);
}
