package com.omarhammad.kdg_backend.orders.adapters.in.messaging;

import com.omarhammad.kdg_backend.common.events.config.RabbitMQTopology;
import com.omarhammad.kdg_backend.common.events.restaurantEvents.OrderReadyForPickUpEvent;
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
public class OrderReadyForPickUpEventListener {


    private OrderStatusProjector projector;

    @RabbitListener(queues = RabbitMQTopology.ORDER_BC_ORDER_READY_QUEUE)
    public void handleOrderReadyForPickUpEvent(OrderReadyForPickUpEvent event) {
        log.info("Order Ready For PickUp : {}", event);

        OrderStatusProjectorCmd cmd = new OrderStatusProjectorCmd(
                new Id<>(event.orderId()),
                OrderStatus.READY_FOR_PICKUP,
                event.occurredAt(),
                null,
                null
        );
        projector.project(cmd);
    }
}
