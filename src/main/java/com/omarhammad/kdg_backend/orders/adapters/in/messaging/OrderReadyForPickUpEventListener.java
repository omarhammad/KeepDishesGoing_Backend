package com.omarhammad.kdg_backend.orders.adapters.in.messaging;

import com.omarhammad.kdg_backend.common.events.config.RabbitMQTopology;
import com.omarhammad.kdg_backend.common.events.restaurantEvents.OrderReadyForPickUpEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderReadyForPickUpEventListener {


    @RabbitListener(queues = RabbitMQTopology.ORDER_READY_FOR_PICKUP_QUEUE)
    public void handleOrderReadyForPickUpEvent(OrderReadyForPickUpEvent event) {
        log.info("Order Ready For PickUp : {}", event);
    }
}
