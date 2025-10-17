package com.omarhammad.kdg_backend.orders.adapters.in.messaging;

import com.omarhammad.kdg_backend.common.events.config.RabbitMQTopology;
import com.omarhammad.kdg_backend.common.events.restaurantEvents.OrderRejectedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderRejectedEventListener {


    @RabbitListener(queues = RabbitMQTopology.ORDER_REJECTED_QUEUE)
    @Async
    public void handleOrderRejectedEvent(OrderRejectedEvent orderRejectedEvent) {
        log.info("Order Rejected event : {}", orderRejectedEvent);
    }
}
