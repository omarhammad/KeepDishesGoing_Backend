package com.omarhammad.kdg_backend.orders.adapters.in.messaging;

import com.omarhammad.kdg_backend.common.events.config.RabbitMQTopology;
import com.omarhammad.kdg_backend.common.events.restaurantEvents.DishOutOfStockEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DishOutOfStockListener {


    @RabbitListener(queues = RabbitMQTopology.DISH_OUT_OF_STOCK_QUEUE)
    public void handleDishOutOfStockEvent(DishOutOfStockEvent event) {
        log.info("Dish OutOfStock Event : {}", event);
    }
}
