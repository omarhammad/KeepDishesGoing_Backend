package com.omarhammad.kdg_backend.orders.core;

import com.omarhammad.kdg_backend.orders.domain.Order;
import com.omarhammad.kdg_backend.orders.ports.in.OrderStatusProjector;
import com.omarhammad.kdg_backend.orders.ports.in.OrderStatusProjectorCmd;
import com.omarhammad.kdg_backend.orders.ports.out.EditOrderPort;
import com.omarhammad.kdg_backend.orders.ports.out.LoadOrderPort;
import com.omarhammad.kdg_backend.orders.core.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DefaultOrderStatusProjector implements OrderStatusProjector {

    private final LoadOrderPort loadOrderPort;
    private final EditOrderPort editOrderPOrt;

    @Override
    public void project(OrderStatusProjectorCmd cmd) {

        Order order = loadOrderPort.findById(cmd.orderId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        order.changeStatusTo(cmd.newOrderStatus()).at(cmd.occurredAt());
        editOrderPOrt.edit(order);
    }
}
