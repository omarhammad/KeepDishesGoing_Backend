package com.omarhammad.kdg_backend.orders.adapters.in.messaging;

import com.omarhammad.kdg_backend.common.events.config.RabbitMQTopology;
import com.omarhammad.kdg_backend.common.events.restaurantEvents.OrderRejectedEvent;
import com.omarhammad.kdg_backend.orders.domain.Id;
import com.omarhammad.kdg_backend.orders.domain.enums.OrderStatus;
import com.omarhammad.kdg_backend.orders.ports.in.OrderStatusProjector;
import com.omarhammad.kdg_backend.orders.ports.in.OrderStatusProjectorCmd;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class OrderRejectedEventListener {


    private OrderStatusProjector orderStatusProjector;

    @RabbitListener(queues = RabbitMQTopology.ORDER_REJECTED_QUEUE)
    @Async
    public void handleOrderRejectedEvent(OrderRejectedEvent event) {
        log.info("Order Rejected event : {}", event);

        OrderStatusProjectorCmd cmd = new OrderStatusProjectorCmd(
                new Id<>(event.orderId()),
                OrderStatus.REJECTED,
                event.occurredAt()
        );
        orderStatusProjector.project(cmd);
    }
}
