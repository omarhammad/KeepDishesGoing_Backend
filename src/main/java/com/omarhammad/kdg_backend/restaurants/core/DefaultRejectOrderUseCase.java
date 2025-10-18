package com.omarhammad.kdg_backend.restaurants.core;

import com.omarhammad.kdg_backend.restaurants.domain.exceptions.OrderBusinessRuleException;
import com.omarhammad.kdg_backend.restaurants.domain.OrderProjection;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.domain.enums.OrderProjectionStatus;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.EntityNotFoundException;
import com.omarhammad.kdg_backend.restaurants.ports.in.RejectOrderCmd;
import com.omarhammad.kdg_backend.restaurants.ports.in.RejectOrderUseCase;
import com.omarhammad.kdg_backend.restaurants.ports.out.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class DefaultRejectOrderUseCase implements RejectOrderUseCase {

    private final EventPublisherPort eventPublisherPort;
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
            case REJECTED -> throw new OrderBusinessRuleException("Order already rejected");
            case ACCEPTED -> throw new OrderBusinessRuleException("Order already accepted");
            case DECLINED -> throw new OrderBusinessRuleException("Order already declined");
            case READY_FOR_PICKUP -> throw new OrderBusinessRuleException("Order already ready for pickup");
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
