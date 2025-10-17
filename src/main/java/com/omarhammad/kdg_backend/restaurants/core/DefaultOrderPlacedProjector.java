package com.omarhammad.kdg_backend.restaurants.core;

import com.omarhammad.kdg_backend.common.events.restaurantEvents.OrderDeclinedEvent;
import com.omarhammad.kdg_backend.restaurants.domain.OrderProjection;
import com.omarhammad.kdg_backend.restaurants.domain.enums.OrderProjectionStatus;
import com.omarhammad.kdg_backend.restaurants.ports.in.OrderPlacedProjectionCmd;
import com.omarhammad.kdg_backend.restaurants.ports.in.OrderPlacedProjector;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadOrderProjection;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadRestaurantPort;
import com.omarhammad.kdg_backend.restaurants.ports.out.EventPublisherPort;
import com.omarhammad.kdg_backend.restaurants.ports.out.SaveOrderProjection;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@AllArgsConstructor
public class DefaultOrderPlacedProjector implements OrderPlacedProjector {


    private final SaveOrderProjection saveOrderProjection;
    private final LoadOrderProjection loadOrderProjection;
    private final LoadRestaurantPort loadRestaurantPort;
    private final EventPublisherPort eventPublisherPort;


    @Override
    public void project(OrderPlacedProjectionCmd cmd) {

        boolean isOrderProjectionExist = loadOrderProjection.findByOrderId(cmd.orderId()).isPresent();
        boolean restaurantExit = loadRestaurantPort.findRestaurantById(cmd.restaurantId()).isPresent();

        if (isOrderProjectionExist) {
            eventPublisherPort.publishOrderDeclined(
                    new OrderDeclinedEvent(cmd.orderId().value(), OrderDeclinedEvent.ERROR_ORDER_ALREADY_EXIST, LocalDateTime.now()));
            return;
        }

        if (!restaurantExit) {
            eventPublisherPort.publishOrderDeclined(
                    new OrderDeclinedEvent(cmd.orderId().value(), OrderDeclinedEvent.ERROR_RESTAURANT_NOT_FOUND, LocalDateTime.now()));
            return;
        }

        OrderProjection newOrderProjection = new OrderProjection(
                cmd.orderId(),
                cmd.restaurantId(),
                OrderProjectionStatus.PLACED,
                cmd.occurredAt());


        saveOrderProjection.save(newOrderProjection);
    }
}
