package com.omarhammad.kdg_backend.restaurants.core;

import com.omarhammad.kdg_backend.restaurants.domain.Coordinates;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.OrderBusinessRuleException;
import com.omarhammad.kdg_backend.restaurants.domain.OrderProjection;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.domain.enums.OrderProjectionStatus;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.EntityNotFoundException;
import com.omarhammad.kdg_backend.restaurants.ports.in.AcceptOrderCmd;
import com.omarhammad.kdg_backend.restaurants.ports.in.AcceptOrderUseCase;
import com.omarhammad.kdg_backend.restaurants.ports.out.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@AllArgsConstructor
public class DefaultAcceptOrderUseCase implements AcceptOrderUseCase {

    private LoadRestaurantPort loadRestaurantPort;
    private LoadOrderProjection loadOrderProjection;
    private EventPublisherPort eventPublisherPort;
    private UpdateOrderProjection updateOrderProjection;
    private GeocodingPort geocodingPort;


    @Override
    public void accept(AcceptOrderCmd cmd) {

        Restaurant restaurant = loadRestaurantPort.findRestaurantById(cmd.restaurantId())
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found"));

        OrderProjection orderProjection = loadOrderProjection.findOrderProjectionByIdAndRestaurantId(cmd.orderProjectionId(), cmd.restaurantId())
                .orElseThrow(() -> new EntityNotFoundException("Order view not found for this restaurant"));


        switch (orderProjection.getStatus()) {
            case REJECTED -> throw new OrderBusinessRuleException("Order already rejected");
            case DECLINED -> throw new OrderBusinessRuleException("Order already declined");
            case ACCEPTED -> throw new OrderBusinessRuleException("Order already accepted");
            case READY_FOR_PICKUP -> throw new OrderBusinessRuleException("Order already ready for pickup");
        }


        Coordinates pickUpCoords = geocodingPort.geocode(restaurant.getAddress());
        Coordinates dropOffCoords = geocodingPort.geocode(orderProjection.getDropOfAddress());
        log.info("pickUpCoords: {}  , dropOffCoords: {}", pickUpCoords, dropOffCoords);
        log.info("pickUpAddress: {}  , dropOffAddress: {}", restaurant.getAddress(), orderProjection.getDropOfAddress());

        LocalDateTime eventOccurredAt = LocalDateTime.now();
        restaurant.acceptOrder(
                orderProjection.getOrderId().value(),
                orderProjection.getDropOfAddress(),
                pickUpCoords,
                dropOffCoords,
                eventOccurredAt
        );

        orderProjection.setStatus(OrderProjectionStatus.ACCEPTED);
        orderProjection.setOccurredAt(eventOccurredAt);

        eventPublisherPort.publishRestaurantEvents(restaurant);
        updateOrderProjection.update(orderProjection);


    }


}
