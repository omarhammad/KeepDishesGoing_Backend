package com.omarhammad.kdg_backend.restaurants.adapters.in.messaging;

import com.omarhammad.kdg_backend.common.events.config.RabbitMQTopology;
import com.omarhammad.kdg_backend.common.events.ordersEvents.OrderPlacedEvent;
import com.omarhammad.kdg_backend.restaurants.domain.Address;
import com.omarhammad.kdg_backend.restaurants.domain.Id;
import com.omarhammad.kdg_backend.restaurants.ports.in.OrderPlacedProjectionCmd;
import com.omarhammad.kdg_backend.restaurants.ports.in.OrderPlacedProjector;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class OrderPlacedEventListener {


    private final OrderPlacedProjector orderPlacedProjector;


    @RabbitListener(queues = RabbitMQTopology.RESTAURANT_BC_ORDER_PLACED_QUEUE)
    @Async
    public void handleOrderPlacedEvent(OrderPlacedEvent event) {
        log.info("Order Placed event : {}", event);

        OrderPlacedProjectionCmd cmd = new OrderPlacedProjectionCmd(
                new Id<>(event.orderId()),
                new Id<>(event.restaurantId()),
                new Address(
                        event.dropOfAddress().street(),
                        event.dropOfAddress().number(),
                        event.dropOfAddress().postalCode(),
                        event.dropOfAddress().city(),
                        event.dropOfAddress().country()
                ),
                event.occurredAt()
        );
        orderPlacedProjector.project(cmd);

    }

}
