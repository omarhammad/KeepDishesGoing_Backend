package com.omarhammad.kdg_backend.restaurants.core;

import com.omarhammad.kdg_backend.common.events.restaurantEvents.OrderDeclinedEvent;
import com.omarhammad.kdg_backend.restaurants.domain.OrderProjection;
import com.omarhammad.kdg_backend.restaurants.domain.enums.OrderProjectionStatus;
import com.omarhammad.kdg_backend.restaurants.ports.in.DeclineOrdersBeyondResponseWindowUseCase;
import com.omarhammad.kdg_backend.restaurants.ports.in.DeclineOrdersCmd;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadOrderProjection;
import com.omarhammad.kdg_backend.restaurants.ports.out.EventPublisherPort;
import com.omarhammad.kdg_backend.restaurants.ports.out.UpdateOrderProjection;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class DefaultDeclineOrdersBeyondResponseWindowUseCase
        implements DeclineOrdersBeyondResponseWindowUseCase {

    private LoadOrderProjection loadOrderProjection;
    private UpdateOrderProjection updateOrderProjection;
    private EventPublisherPort eventPublisherPort;

    @Override
    public void decline(DeclineOrdersCmd cmd) {


        List<OrderProjection> placedOrdersProjections = loadOrderProjection.findAll()
                .stream()
                .filter(orderProjection ->
                        orderProjection.getStatus().equals(OrderProjectionStatus.PLACED))
                .toList();

        if (placedOrdersProjections.isEmpty()) return;

        for (OrderProjection placedOrderProjection : placedOrdersProjections) {
            if (placedOrderProjection.getOccurredAt().plusMinutes(cmd.minutes())
                    .isAfter(LocalDateTime.now())) continue;

            OrderDeclinedEvent event = new OrderDeclinedEvent(
                    placedOrderProjection.getOrderId().value(),
                    placedOrderProjection.getRestaurantId().value(),
                    OrderDeclinedEvent.ERROR_RESPONSE_TIME_EXCEEDED,
                    LocalDateTime.now()
            );

            placedOrderProjection.setStatus(OrderProjectionStatus.DECLINED);
            placedOrderProjection.setDeclinedReason(OrderDeclinedEvent.ERROR_RESPONSE_TIME_EXCEEDED);
            placedOrderProjection.setOccurredAt(event.occurredAt());
            updateOrderProjection.update(placedOrderProjection);

            eventPublisherPort.publishOrderDeclined(event);
        }

    }
}
