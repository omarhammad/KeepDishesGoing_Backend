package com.omarhammad.kdg_backend.restaurants.core;

import com.omarhammad.kdg_backend.common.events.restaurantEvents.OrderRejectedEvent;
import com.omarhammad.kdg_backend.restaurants.domain.IncomingOrder;
import com.omarhammad.kdg_backend.restaurants.domain.enums.IncomingOrderStatus;
import com.omarhammad.kdg_backend.restaurants.ports.in.OrderPlacedProjectionCmd;
import com.omarhammad.kdg_backend.restaurants.ports.in.OrderPlacedProjector;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadRestaurantPort;
import com.omarhammad.kdg_backend.restaurants.ports.out.RestaurantEventPublisherPort;
import com.omarhammad.kdg_backend.restaurants.ports.out.SaveIncomingOrder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@AllArgsConstructor
public class DefaultOrderPlacedProjector implements OrderPlacedProjector {


    private final SaveIncomingOrder saveIncomingOrder;
    private final LoadRestaurantPort loadRestaurantPort;
    private final RestaurantEventPublisherPort eventPublisherPort;


    @Override
    public void project(OrderPlacedProjectionCmd cmd) {

        boolean restaurantExit = loadRestaurantPort.findRestaurantById(cmd.restaurantId()).isPresent();

        if (!restaurantExit) {
            log.info("Restaurant not found");
            eventPublisherPort.publishOrderRejected(
                    new OrderRejectedEvent(cmd.orderId().value(), "Restaurant not found", LocalDateTime.now()));
            return;
        }
        IncomingOrder incomingOrder = new IncomingOrder(
                cmd.orderId(),
                cmd.restaurantId(),
                IncomingOrderStatus.PLACED,
                cmd.occurredAt()
        );
        saveIncomingOrder.save(incomingOrder);
    }
}
