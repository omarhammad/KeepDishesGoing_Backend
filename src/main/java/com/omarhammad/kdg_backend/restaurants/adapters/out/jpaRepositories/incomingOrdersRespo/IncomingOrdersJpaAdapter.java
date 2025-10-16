package com.omarhammad.kdg_backend.restaurants.adapters.out.jpaRepositories.incomingOrdersRespo;

import com.omarhammad.kdg_backend.restaurants.adapters.out.jpaRepositories.incomingOrdersRespo.entities.IncomingOrdersJpaEntity;
import com.omarhammad.kdg_backend.restaurants.domain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.IncomingOrder;
import com.omarhammad.kdg_backend.restaurants.domain.enums.IncomingOrderStatus;
import com.omarhammad.kdg_backend.restaurants.ports.out.SaveIncomingOrder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class IncomingOrdersJpaAdapter implements SaveIncomingOrder {

    private final IncomingOrdersRepository repository;


    @Override
    public Optional<IncomingOrder> save(IncomingOrder incomingOrder) {
        IncomingOrdersJpaEntity entity = toIncomingOrderJpaEntity(incomingOrder);
        return Optional.of(toIncomingOrder(repository.save(entity)));
    }


    private IncomingOrder toIncomingOrder(IncomingOrdersJpaEntity entity) {
        return new IncomingOrder(
                new Id<>(entity.getOrderId().toString()),
                new Id<>(entity.getRestaurantId().toString()),
                IncomingOrderStatus.valueOf(entity.getStatus().toUpperCase()),
                entity.getOccurredAt()
        );
    }

    private IncomingOrdersJpaEntity toIncomingOrderJpaEntity(IncomingOrder incomingOrder) {
        return new IncomingOrdersJpaEntity(
                UUID.fromString(incomingOrder.getOrderId().value()),
                UUID.fromString(incomingOrder.getRestaurantId().value()),
                incomingOrder.getOccurredAt()
        );
    }
}
