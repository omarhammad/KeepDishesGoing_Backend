package com.omarhammad.kdg_backend.orders.ports.in;

import com.omarhammad.kdg_backend.orders.domain.Id;
import com.omarhammad.kdg_backend.orders.domain.Order;

public interface FindProcessedOrderByIdUseCase {

    Order findProcessedOrdersById(Id<Order> orderId);


}
