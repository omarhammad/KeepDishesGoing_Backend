package com.omarhammad.kdg_backend.orders.core;

import com.omarhammad.kdg_backend.orders.domain.Id;
import com.omarhammad.kdg_backend.orders.domain.Order;
import com.omarhammad.kdg_backend.orders.domain.enums.OrderStatus;
import com.omarhammad.kdg_backend.orders.ports.in.FindProcessedOrdersByRestaurantId;
import com.omarhammad.kdg_backend.orders.ports.out.LoadOrderPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DefaultFindProcessedOrdersByRestaurantId implements FindProcessedOrdersByRestaurantId {

    private LoadOrderPort loadOrderPort;

    @Override
    public List<Order> findProcessedOrdersByRestaurantId(Id restaurantId) {

        List<Order> orders = loadOrderPort.findOrdersByRestaurantId(restaurantId);



        return orders
                .stream()
                .filter(order -> !order.getOrderStatus().equals(OrderStatus.PENDING_PAYMENT))
                .toList();
    }
}
