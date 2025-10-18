package com.omarhammad.kdg_backend.orders.adapters.in.messaging;

import com.omarhammad.kdg_backend.common.events.config.RabbitMQTopology;
import com.omarhammad.kdg_backend.common.events.restaurantEvents.DishInStockEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class DishInStockListener {


    @RabbitListener(queues = RabbitMQTopology.DISH_IN_STOCK_QUEUE)
    public void handleDishInStockEvent(DishInStockEvent event) {
        log.info("Dish InStock Event : {}", event);
    }
}
