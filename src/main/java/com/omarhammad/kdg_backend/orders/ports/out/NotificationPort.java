package com.omarhammad.kdg_backend.orders.ports.out;

import com.omarhammad.kdg_backend.orders.domain.Order;

public interface NotificationPort {


    void notify(Order order);

}
