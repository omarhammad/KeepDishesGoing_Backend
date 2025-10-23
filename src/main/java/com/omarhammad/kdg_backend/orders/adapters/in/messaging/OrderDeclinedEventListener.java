package com.omarhammad.kdg_backend.orders.adapters.in.messaging;

import com.omarhammad.kdg_backend.common.events.config.RabbitMQTopology;
import com.omarhammad.kdg_backend.common.events.restaurantEvents.OrderDeclinedEvent;
import com.omarhammad.kdg_backend.orders.domain.Id;
import com.omarhammad.kdg_backend.orders.domain.enums.OrderStatus;
import com.omarhammad.kdg_backend.orders.ports.in.OrderStatusProjector;
import com.omarhammad.kdg_backend.orders.ports.in.OrderStatusProjectorCmd;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class OrderDeclinedEventListener {

    private final OrderStatusProjector projector;

    @RabbitListener(queues = RabbitMQTopology.ORDER_BC_ORDER_DECLINED_QUEUE)
    public void handleOrderDeclinedEvent(OrderDeclinedEvent event) {
        log.info("Order Declined event: {}", event);

        OrderStatusProjectorCmd cmd = new OrderStatusProjectorCmd(
                new Id<>(event.orderId()),
                OrderStatus.DECLINED,
                event.occurredAt(),
                null,
                event.declinedMsg()
        );
        projector.project(cmd);
    }
}
