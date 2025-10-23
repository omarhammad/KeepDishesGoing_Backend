package com.omarhammad.kdg_backend.orders.adapters.in.messaging;

import com.omarhammad.kdg_backend.common.events.config.RabbitMQTopology;
import com.omarhammad.kdg_backend.common.events.deliveryEvents.OrderPickedUpEvent;
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
public class OrderContextOrderPickedupListener {


    private OrderStatusProjector projector;

    @RabbitListener(queues = RabbitMQTopology.ORDER_BC_ORDER_PICKEDUP_QUEUE)
    public void handleOrderPickedupEvent(OrderPickedUpEvent event) {

        log.info("OrderContext:Order Pickedup Event: {}", event);

        OrderStatusProjectorCmd cmd = new OrderStatusProjectorCmd(
                new Id<>(event.orderId()),
                OrderStatus.PICKED_UP,
                event.occurredAt(),
                null,
                null
        );
        projector.project(cmd);

    }
}
