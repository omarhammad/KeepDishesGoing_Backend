package com.omarhammad.kdg_backend.orders.adapters.in.messaging;

import com.omarhammad.kdg_backend.common.events.config.RabbitMQTopology;
import com.omarhammad.kdg_backend.common.events.restaurantEvents.OrderAcceptedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderAcceptedEventListener {


    @RabbitListener(queues = RabbitMQTopology.ORDER_ACCEPTED_QUEUE)
    public void handleOrderAcceptedEvent(OrderAcceptedEvent event) {
        log.info("Order Accepted event : {}", event);
    }
}
