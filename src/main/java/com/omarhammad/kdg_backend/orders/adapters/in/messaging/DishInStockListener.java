package com.omarhammad.kdg_backend.orders.adapters.in.messaging;

import com.omarhammad.kdg_backend.common.events.config.RabbitMQTopology;
import com.omarhammad.kdg_backend.common.events.restaurantEvents.DishInStockEvent;
import com.omarhammad.kdg_backend.orders.domain.Id;
import com.omarhammad.kdg_backend.orders.domain.enums.DishStockStatus;
import com.omarhammad.kdg_backend.orders.ports.in.DishStockStatusProjector;
import com.omarhammad.kdg_backend.orders.ports.in.DishStockStatusProjectorCmd;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class DishInStockListener {

    private final DishStockStatusProjector projector;


    @RabbitListener(queues = RabbitMQTopology.DISH_IN_STOCK_QUEUE)
    public void handleDishInStockEvent(DishInStockEvent event) {
        log.info("Dish InStock Event : {}", event);

        DishStockStatusProjectorCmd cmd = new DishStockStatusProjectorCmd(
                new Id<>(event.dishId()),
                new Id(event.restaurantId()),
                DishStockStatus.IN_STOCK,
                event.occurredAt()
        );

        projector.project(cmd);
    }
}
