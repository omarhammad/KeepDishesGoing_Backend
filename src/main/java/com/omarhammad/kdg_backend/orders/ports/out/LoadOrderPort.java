package com.omarhammad.kdg_backend.orders.ports.out;

import com.omarhammad.kdg_backend.orders.domain.Order;
import com.omarhammad.kdg_backend.orders.domain.Id;

import java.util.Optional;

public interface LoadOrderPort {

    Optional<Order> findById(Id<Order> orderId);
}
