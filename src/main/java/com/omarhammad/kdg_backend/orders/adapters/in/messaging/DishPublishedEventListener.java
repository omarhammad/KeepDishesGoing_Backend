package com.omarhammad.kdg_backend.orders.adapters.in.messaging;

import com.omarhammad.kdg_backend.common.events.config.RabbitMQTopology;
import com.omarhammad.kdg_backend.common.events.restaurantEvents.DishPublishedEvent;
import com.omarhammad.kdg_backend.orders.domain.Id;
import com.omarhammad.kdg_backend.orders.domain.enums.DishLiveStatus;
import com.omarhammad.kdg_backend.orders.ports.in.DishLiveStatusProjector;
import com.omarhammad.kdg_backend.orders.ports.in.DishLiveStatusProjectorCmd;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class DishPublishedEventListener {

    private final DishLiveStatusProjector projector;


    @RabbitListener(queues = RabbitMQTopology.DISH_PUBLISHED_QUEUE)
    public void handleDishPublishedEvent(DishPublishedEvent event) {
        log.info("Dish Published Event : {}", event);

        DishLiveStatusProjectorCmd cmd = new DishLiveStatusProjectorCmd(
                new Id<>(event.dishId()),
                new Id<>(event.restaurantId()),
                event.price(),
                DishLiveStatus.PUBLISHED,
                event.occurredAt()
        );

        projector.project(cmd);
    }
}
