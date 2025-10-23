package com.omarhammad.kdg_backend.restaurants.adapters.in.messaging;

import com.omarhammad.kdg_backend.common.events.config.RabbitMQTopology;
import com.omarhammad.kdg_backend.common.events.deliveryEvents.OrderDeliveredEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class OrderDeliveredListener {

    @RabbitListener(queues = RabbitMQTopology.RESTAURANT_BC_ORDER_DELIVERED_QUEUE)
    public void handleOrderPickedupEvent(OrderDeliveredEvent event) {
        log.info("Order Delivered Event: {}", event);
    }
}