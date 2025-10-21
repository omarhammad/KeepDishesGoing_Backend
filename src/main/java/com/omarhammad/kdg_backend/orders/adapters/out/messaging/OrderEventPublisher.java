package com.omarhammad.kdg_backend.orders.adapters.out.messaging;

import com.omarhammad.kdg_backend.orders.domain.Order;
import com.omarhammad.kdg_backend.orders.ports.out.EventPublisherPort;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderEventPublisher implements EventPublisherPort {

    private final ApplicationEventPublisher publisher;

    @Override
    @Transactional
    public void publish(Order order) {
        order.getDomainEvents().forEach(publisher::publishEvent);
    }
}
