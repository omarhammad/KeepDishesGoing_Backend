package com.omarhammad.kdg_backend.orders.ports.in;

import com.omarhammad.kdg_backend.orders.domain.Id;
import com.omarhammad.kdg_backend.orders.domain.Order;

public interface FindCheckedOutrderByIdUseCase {

    Order findCheckedOutOrdersById(Id<Order> orderId);


}
