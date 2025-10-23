package com.omarhammad.kdg_backend.restaurants.adapters.in.messaging;

import com.omarhammad.kdg_backend.common.events.config.RabbitMQTopology;
import com.omarhammad.kdg_backend.common.events.deliveryEvents.OrderPickedUpEvent;
import com.omarhammad.kdg_backend.restaurants.domain.Id;
import com.omarhammad.kdg_backend.restaurants.ports.in.OrderPickedupProjector;
import com.omarhammad.kdg_backend.restaurants.ports.in.OrderPickedupProjectorCmd;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class RestaurantContextOrderPickedupListener {

    private final OrderPickedupProjector projector;

    @RabbitListener(queues = RabbitMQTopology.RESTAURANT_BC_ORDER_PICKEDUP_QUEUE)
    public void handleOrderPickedupEvent(OrderPickedUpEvent event) {
        log.info("Order Pickedup Event: {}", event);

        OrderPickedupProjectorCmd cmd = new OrderPickedupProjectorCmd(
                new Id<>(event.orderId()),
                new Id<>(event.restaurantId()),
                event.occurredAt()
        );

        projector.project(cmd);

    }
}
