package com.omarhammad.kdg_backend.restaurants.core;

import com.omarhammad.kdg_backend.common.events.restaurantEvents.OrderRejectedEvent;
import com.omarhammad.kdg_backend.restaurants.domain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.OrderProjection;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.domain.enums.OrderProjectionStatus;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.EntityNotFoundException;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.OrderAlreadyAcceptedException;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.OrderAlreadyDeclinedException;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.OrderAlreadyRejectedException;
import com.omarhammad.kdg_backend.restaurants.ports.in.RejectOrderCmd;
import com.omarhammad.kdg_backend.restaurants.ports.in.RejectOrderUseCase;
import com.omarhammad.kdg_backend.restaurants.ports.out.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class DefaultRejectOrderUseCase implements RejectOrderUseCase {

    private final RestaurantEventPublisherPort eventPublisherPort;
    private final UpdateOrderProjection updateOrderProjection;
    private final LoadOrderProjection loadOrderProjection;
    private final LoadRestaurantPort loadRestaurantPort;

    @Override
    public void reject(RejectOrderCmd cmd) {

        Restaurant restaurant = loadRestaurantPort.findRestaurantById(cmd.restaurantId())
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found"));

        OrderProjection orderProjection = loadOrderProjection.findOrderProjectionByIdAndRestaurantId(cmd.orderProjectionId(), cmd.restaurantId())
                .orElseThrow(() -> new EntityNotFoundException("Order view not found for this restaurant"));


        switch (orderProjection.getStatus()) {
            case REJECTED -> throw new OrderAlreadyRejectedException("Order already rejected");
            case ACCEPTED -> throw new OrderAlreadyAcceptedException("Order already accepted");
            case DECLINED -> throw new OrderAlreadyDeclinedException("Order already accepted");
        }


        LocalDateTime eventOccurredAt = LocalDateTime.now();
        restaurant.rejectOrder(
                orderProjection.getOrderId().value(),
                cmd.reason(),
                eventOccurredAt
        );

        orderProjection.setRejectedReason(cmd.reason());
        orderProjection.setStatus(OrderProjectionStatus.REJECTED);
        orderProjection.setOccurredAt(eventOccurredAt);

        eventPublisherPort.publishRestaurantEvents(restaurant);
        updateOrderProjection.update(orderProjection);


    }
}
