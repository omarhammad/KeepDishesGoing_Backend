package com.omarhammad.kdg_backend.orders.adapters.in.messaging;

import com.omarhammad.kdg_backend.common.events.config.RabbitMQTopology;
import com.omarhammad.kdg_backend.common.events.restaurantEvents.DishUnPublishedEvent;
import com.omarhammad.kdg_backend.orders.core.DefaultDishLiveStatusProjector;
import com.omarhammad.kdg_backend.orders.domain.Id;
import com.omarhammad.kdg_backend.orders.domain.enums.DishLiveStatus;
import com.omarhammad.kdg_backend.orders.ports.in.DishLiveStatusProjector;
import com.omarhammad.kdg_backend.orders.ports.in.DishLiveStatusProjectorCmd;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class DishUnPublishedListener {


    private DishLiveStatusProjector projector;

    @RabbitListener(queues = RabbitMQTopology.ORDER_BC_DISH_UNPUBLISHED_QUEUE)
    public void handleDishUnPublishedEvent(DishUnPublishedEvent event) {
        log.info("Dish UnPublished Event : {}", event);
        DishLiveStatusProjectorCmd cmd = new DishLiveStatusProjectorCmd(
                new Id<>(event.dishId()),
                new Id<>(event.restaurantId()),
                null,
                DishLiveStatus.UNPUBLISHED,
                event.occurredAt()
        );
        projector.project(cmd);
    }
}
