package com.omarhammad.kdg_backend.restaurants.adapters.out.jpaRepositories.OrdersProjectionsRespo;

import com.omarhammad.kdg_backend.restaurants.adapters.out.jpaRepositories.OrdersProjectionsRespo.entities.OrderProjectionJpaEntity;
import com.omarhammad.kdg_backend.restaurants.domain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.OrderProjection;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.domain.enums.OrderProjectionStatus;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadOrderProjection;
import com.omarhammad.kdg_backend.restaurants.ports.out.SaveOrderProjection;
import com.omarhammad.kdg_backend.restaurants.ports.out.UpdateOrderProjection;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class OrdersProjectionsJpaAdapter implements SaveOrderProjection, UpdateOrderProjection, LoadOrderProjection {

    private final OrdersProjectionsRepository repository;


    @Override
    public Optional<OrderProjection> findOrderProjectionByIdAndRestaurantId(Id<OrderProjection> orderProjectionId, Id<Restaurant> restaurantId) {
        return repository.findByOrderIdAndRestaurantId(UUID.fromString(orderProjectionId.value()), UUID.fromString(restaurantId.value()))
                .map(this::toOrderProjection);

    }

    @Override
    public Optional<OrderProjection> findByOrderId(Id<OrderProjection> orderProjectionId) {
        return repository.findById(UUID.fromString(orderProjectionId.value()))
                .map(this::toOrderProjection);
    }

    @Override
    public Optional<OrderProjection> save(OrderProjection orderProjection) {
        OrderProjectionJpaEntity entity = toOrderProjectionJpaEntity(orderProjection);
        return Optional.of(toOrderProjection(repository.save(entity)));
    }


    @Override
    public void update(OrderProjection orderProjection) {
        OrderProjectionJpaEntity entity = repository.findById(UUID.fromString(orderProjection.getOrderId().value())).orElse(null);

        if (Objects.isNull(entity)) return;

        entity.setStatus(orderProjection.getStatus().toString());
        entity.setRejectedReason(orderProjection.getRejectedReason());
        entity.setDeclinedReason(orderProjection.getDeclinedReason());
        entity.setOccurredAt(orderProjection.getOccurredAt());

        repository.save(entity);
    }


    private OrderProjection toOrderProjection(OrderProjectionJpaEntity entity) {
        return new OrderProjection(
                new Id<>(entity.getOrderId().toString()),
                new Id<>(entity.getRestaurantId().toString()),
                OrderProjectionStatus.valueOf(entity.getStatus().toUpperCase()),
                entity.getOccurredAt()
        );
    }

    private OrderProjectionJpaEntity toOrderProjectionJpaEntity(OrderProjection orderProjection) {
        return new OrderProjectionJpaEntity(
                UUID.fromString(orderProjection.getOrderId().value()),
                UUID.fromString(orderProjection.getRestaurantId().value()),
                orderProjection.getStatus().toString(),
                orderProjection.getOccurredAt()
        );
    }


}
