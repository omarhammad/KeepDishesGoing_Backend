package com.omarhammad.kdg_backend.orders.adapters.in.messaging;

import com.omarhammad.kdg_backend.common.events.config.RabbitMQTopology;
import com.omarhammad.kdg_backend.common.events.deliveryEvents.OrderDeliveredEvent;
import com.omarhammad.kdg_backend.orders.domain.Id;
import com.omarhammad.kdg_backend.orders.domain.enums.OrderStatus;
import com.omarhammad.kdg_backend.orders.ports.in.OrderStatusProjector;
import com.omarhammad.kdg_backend.orders.ports.in.OrderStatusProjectorCmd;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class OrderContextOrderDeliveredListener {


    private final OrderStatusProjector projector;


    @RabbitListener(queues = RabbitMQTopology.ORDER_BC_ORDER_DELIVERED_QUEUE)
    public void handleOrderDeliveredEvent(OrderDeliveredEvent event) {
        log.info("OrderContext:Order Delivered Event: {}", event);

        OrderStatusProjectorCmd cmd = new OrderStatusProjectorCmd(
                new Id<>(event.orderId()),
                OrderStatus.DELIVERED,
                event.occurredAt()
        );
        projector.project(cmd);
    }


}
