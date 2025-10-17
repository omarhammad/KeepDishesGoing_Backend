package com.omarhammad.kdg_backend.restaurants.core;

import com.omarhammad.kdg_backend.restaurants.domain.OrderProjection;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.domain.enums.OrderProjectionStatus;
import com.omarhammad.kdg_backend.restaurants.ports.in.AutoMarkOrdersReadyForPickUpUseCase;
import com.omarhammad.kdg_backend.restaurants.ports.out.EventPublisherPort;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadOrderProjection;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadRestaurantPort;
import com.omarhammad.kdg_backend.restaurants.ports.out.UpdateOrderProjection;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class DefaultAutoMarkOrdersReadyForPickUpUseCase implements AutoMarkOrdersReadyForPickUpUseCase {


    private LoadOrderProjection loadOrderProjection;
    private LoadRestaurantPort loadRestaurantPort;
    private EventPublisherPort eventPublisherPort;
    private UpdateOrderProjection updateOrderProjection;

    @Override
    public void readyForPickUp() {

        List<OrderProjection> ordersProjections = loadOrderProjection.findAll()
                .stream()
                .filter(orderProjection ->
                        orderProjection.getStatus().equals(OrderProjectionStatus.ACCEPTED))
                .toList();

        for (OrderProjection orderProjection : ordersProjections) {
            Restaurant restaurant = loadRestaurantPort.findRestaurantById(orderProjection.getRestaurantId()).orElse(null);
            if (Objects.isNull(restaurant)) continue;
            int defaultPrepTime = restaurant.getDefaultPrepTime();
            if (orderProjection.getOccurredAt().plusMinutes(defaultPrepTime).isAfter(LocalDateTime.now())) continue;

            LocalDateTime occurredAt = LocalDateTime.now();
            restaurant.readyForPickUp(orderProjection.getOrderId().value(), occurredAt);

            eventPublisherPort.publishRestaurantEvents(restaurant);

            orderProjection.setStatus(OrderProjectionStatus.READY_FOR_PICKUP);
            orderProjection.setOccurredAt(occurredAt);
            updateOrderProjection.update(orderProjection);

        }


    }
}
