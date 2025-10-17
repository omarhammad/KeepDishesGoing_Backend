package com.omarhammad.kdg_backend.orders.adapters.in.messaging;

import com.omarhammad.kdg_backend.common.events.config.RabbitMQTopology;
import com.omarhammad.kdg_backend.common.events.restaurantEvents.OrderDeclinedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderDeclinedEventListener {


    @RabbitListener(queues = RabbitMQTopology.ORDER_DECLINED_QUEUE)
    public void handleOrderDeclinedEvent(OrderDeclinedEvent orderDeclinedEvent) {
        log.info("Order Declined event: {}", orderDeclinedEvent);
    }
}
