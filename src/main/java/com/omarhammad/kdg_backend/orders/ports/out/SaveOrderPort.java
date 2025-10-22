package com.omarhammad.kdg_backend.orders.ports.out;

import com.omarhammad.kdg_backend.orders.domain.Order;

public interface SaveOrderPort {

    Order save(Order order);

}
