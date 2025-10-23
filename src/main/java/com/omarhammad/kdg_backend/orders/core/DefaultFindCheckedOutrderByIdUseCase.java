package com.omarhammad.kdg_backend.orders.core;

import com.omarhammad.kdg_backend.orders.core.exceptions.OrderNotProcessedException;
import com.omarhammad.kdg_backend.orders.domain.Id;
import com.omarhammad.kdg_backend.orders.domain.Order;
import com.omarhammad.kdg_backend.orders.domain.enums.OrderStatus;
import com.omarhammad.kdg_backend.orders.ports.in.FindCheckedOutrderByIdUseCase;
import com.omarhammad.kdg_backend.orders.ports.out.LoadOrderPort;
import com.omarhammad.kdg_backend.orders.core.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DefaultFindCheckedOutrderByIdUseCase implements FindCheckedOutrderByIdUseCase {


    private LoadOrderPort loadOrderPort;

    @Override
    public Order findCheckedOutOrdersById(Id<Order> orderId) {

        Order order = loadOrderPort.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        if (order.getOrderStatus().equals(OrderStatus.PENDING_PAYMENT))
            throw new OrderNotProcessedException("Order is not checked out yet");

        return order;
    }
}
