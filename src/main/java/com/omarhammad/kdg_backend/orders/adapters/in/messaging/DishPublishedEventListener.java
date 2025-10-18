package com.omarhammad.kdg_backend.orders.adapters.in.messaging;

import com.omarhammad.kdg_backend.common.events.config.RabbitMQTopology;
import com.omarhammad.kdg_backend.common.events.restaurantEvents.DishPublishedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DishPublishedEventListener {



    @RabbitListener(queues = RabbitMQTopology.DISH_PUBLISHED_QUEUE)
    public void handleDishPublishedEvent(DishPublishedEvent event) {
        log.info("Dish Published Event : {}", event);
    }
}
