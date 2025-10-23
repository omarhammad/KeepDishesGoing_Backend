package com.omarhammad.kdg_backend.restaurants.adapters.in.messaging;

import com.omarhammad.kdg_backend.common.events.config.RabbitMQTopology;
import com.omarhammad.kdg_backend.common.events.deliveryEvents.OrderDeliveredEvent;
import com.omarhammad.kdg_backend.restaurants.domain.Id;
import com.omarhammad.kdg_backend.restaurants.ports.in.OrderDeliveredProjector;
import com.omarhammad.kdg_backend.restaurants.ports.in.OrderDeliveredProjectorCmd;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class RestaurantContextOrderDeliveredListener {

    private final OrderDeliveredProjector projector;

    @RabbitListener(queues = RabbitMQTopology.RESTAURANT_BC_ORDER_DELIVERED_QUEUE)
    public void handleOrderPickedupEvent(OrderDeliveredEvent event) {
        log.info("Order Delivered Event: {}", event);


        OrderDeliveredProjectorCmd cmd = new OrderDeliveredProjectorCmd(
                new Id<>(event.orderId()),
                new Id<>(event.restaurantId()),
                event.occurredAt()
        );

        projector.project(cmd);

    }
}