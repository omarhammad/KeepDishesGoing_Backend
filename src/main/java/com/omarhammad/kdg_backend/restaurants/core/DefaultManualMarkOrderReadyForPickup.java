package com.omarhammad.kdg_backend.restaurants.core;

import com.omarhammad.kdg_backend.restaurants.core.exceptions.OrderBusinessRuleException;
import com.omarhammad.kdg_backend.restaurants.domain.OrderProjection;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.domain.enums.OrderProjectionStatus;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.EntityNotFoundException;
import com.omarhammad.kdg_backend.restaurants.ports.in.ManualMarkOrderReadyForPickup;
import com.omarhammad.kdg_backend.restaurants.ports.in.ReadyForPickupCmd;
import com.omarhammad.kdg_backend.restaurants.ports.out.EventPublisherPort;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadOrderProjection;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadRestaurantPort;
import com.omarhammad.kdg_backend.restaurants.ports.out.UpdateOrderProjection;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class DefaultManualMarkOrderReadyForPickup implements ManualMarkOrderReadyForPickup {


    private LoadRestaurantPort loadRestaurantPort;
    private LoadOrderProjection loadOrderProjection;
    private EventPublisherPort eventPublisherPort;
    private UpdateOrderProjection updateOrderProjection;

    @Override
    public void readyForPickup(ReadyForPickupCmd cmd) {
        Restaurant restaurant = loadRestaurantPort.findRestaurantById(cmd.restaurantId())
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found"));

        OrderProjection orderProjection = loadOrderProjection.findOrderProjectionByIdAndRestaurantId(cmd.orderProjectionId(), cmd.restaurantId())
                .orElseThrow(() -> new EntityNotFoundException("Order view not found for this restaurant"));


        switch (orderProjection.getStatus()) {
            case REJECTED -> throw new OrderBusinessRuleException("Order already rejected");
            case DECLINED -> throw new OrderBusinessRuleException("Order already declined");
            case READY_FOR_PICKUP -> throw new OrderBusinessRuleException("Order already ready for pickup");
            case PLACED -> throw new OrderBusinessRuleException("Order not yet accepted");
        }


        LocalDateTime eventOccurredAt = LocalDateTime.now();
        restaurant.readyForPickUp(
                orderProjection.getOrderId().value(),
                eventOccurredAt
        );

        orderProjection.setStatus(OrderProjectionStatus.READY_FOR_PICKUP);
        orderProjection.setOccurredAt(eventOccurredAt);

        eventPublisherPort.publishRestaurantEvents(restaurant);
        updateOrderProjection.update(orderProjection);

    }
}
