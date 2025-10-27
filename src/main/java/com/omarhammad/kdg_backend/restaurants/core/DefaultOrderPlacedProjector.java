package com.omarhammad.kdg_backend.restaurants.core;

import com.omarhammad.kdg_backend.common.events.restaurantEvents.OrderDeclinedEvent;
import com.omarhammad.kdg_backend.restaurants.domain.OrderProjection;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
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
import java.util.Objects;

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

        Restaurant restaurant = loadRestaurantPort.findRestaurantById(cmd.restaurantId()).orElse(null);
        boolean isOrderProjectionExist = loadOrderProjection.findByOrderId(cmd.orderId()).isPresent();

        if (Objects.isNull(restaurant) || isOrderProjectionExist) return;


        OrderProjection newOrderProjection = new OrderProjection(
                cmd.orderId(),
                cmd.restaurantId(),
                OrderProjectionStatus.PLACED,
                cmd.dropOfAddress(),
                cmd.occurredAt());


        saveOrderProjection.save(newOrderProjection);
    }
}
