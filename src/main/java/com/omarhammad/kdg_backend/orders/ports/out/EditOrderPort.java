package com.omarhammad.kdg_backend.orders.ports.out;

import com.omarhammad.kdg_backend.orders.domain.Order;

public interface EditOrderPort {
    void edit(Order order);
}
