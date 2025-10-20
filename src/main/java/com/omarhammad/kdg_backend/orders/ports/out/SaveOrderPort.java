package com.omarhammad.kdg_backend.orders.ports.out;

import com.omarhammad.kdg_backend.orders.domain.Order;

import java.util.Optional;

public interface SaveOrderPort {

    Optional<Order> save(Order order);

}
