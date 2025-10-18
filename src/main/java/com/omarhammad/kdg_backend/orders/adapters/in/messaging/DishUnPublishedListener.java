package com.omarhammad.kdg_backend.orders.adapters.in.messaging;

import com.omarhammad.kdg_backend.common.events.config.RabbitMQTopology;
import com.omarhammad.kdg_backend.common.events.restaurantEvents.DishUnPublishedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DishUnPublishedListener {


    @RabbitListener(queues = RabbitMQTopology.DISH_UNPUBLISHED_QUEUE)
    public void handleDishUnPublishedEvent(DishUnPublishedEvent event) {
        log.info("Dish UnPublished Event : {}", event);
    }
}
