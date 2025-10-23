package com.omarhammad.kdg_backend.orders.ports.in;

import com.omarhammad.kdg_backend.orders.domain.Order;
import com.omarhammad.kdg_backend.orders.domain.Id;
import com.omarhammad.kdg_backend.orders.domain.enums.OrderStatus;

import java.time.LocalDateTime;

public record OrderStatusProjectorCmd(Id<Order> orderId, OrderStatus newOrderStatus, LocalDateTime occurredAt,
                                      String rejectedMessage, String declinedMessage) {
}
