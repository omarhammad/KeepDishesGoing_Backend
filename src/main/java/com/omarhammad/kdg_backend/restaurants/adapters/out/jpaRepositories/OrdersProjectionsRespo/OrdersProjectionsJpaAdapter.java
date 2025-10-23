package com.omarhammad.kdg_backend.restaurants.adapters.out.jpaRepositories.OrdersProjectionsRespo;

import com.omarhammad.kdg_backend.restaurants.adapters.out.jpaRepositories.OrdersProjectionsRespo.entities.DropOffAddressJpa;
import com.omarhammad.kdg_backend.restaurants.adapters.out.jpaRepositories.OrdersProjectionsRespo.entities.OrderProjectionJpaEntity;
import com.omarhammad.kdg_backend.restaurants.domain.Address;
import com.omarhammad.kdg_backend.restaurants.domain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.OrderProjection;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.domain.enums.OrderProjectionStatus;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadOrderProjection;
import com.omarhammad.kdg_backend.restaurants.ports.out.SaveOrderProjection;
import com.omarhammad.kdg_backend.restaurants.ports.out.UpdateOrderProjection;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class OrdersProjectionsJpaAdapter implements SaveOrderProjection, UpdateOrderProjection, LoadOrderProjection {

    private final OrdersProjectionsRepository repository;


    @Override
    public List<OrderProjection> findAll() {
        return repository.findAll().stream().map(this::toOrderProjection).toList();
    }

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
        OrderProjectionJpaEntity entity = toOrderProjectionJpaEntity(orderProjection);
        repository.save(entity);
    }


    private OrderProjection toOrderProjection(OrderProjectionJpaEntity entity) {
        return new OrderProjection(
                new Id<>(entity.getOrderId().toString()),
                new Id<>(entity.getRestaurantId().toString()),
                OrderProjectionStatus.valueOf(entity.getStatus().toUpperCase()),
                toDropOfAddress(entity.getDropOfAddress()),
                entity.getOccurredAt()
        );
    }

    private Address toDropOfAddress(DropOffAddressJpa dropOfAddress) {
        return new Address(
                dropOfAddress.street(),
                dropOfAddress.number(),
                dropOfAddress.postalCode(),
                dropOfAddress.city(),
                dropOfAddress.country()
        );
    }

    private OrderProjectionJpaEntity toOrderProjectionJpaEntity(OrderProjection orderProjection) {
        return new OrderProjectionJpaEntity(
                UUID.fromString(orderProjection.getOrderId().value()),
                UUID.fromString(orderProjection.getRestaurantId().value()),
                orderProjection.getStatus().toString(),
                orderProjection.getRejectedReason(),
                orderProjection.getDeclinedReason(),
                toDropOfAddressJpa(orderProjection.getDropOfAddress()),
                orderProjection.getOccurredAt()
        );
    }

    private DropOffAddressJpa toDropOfAddressJpa(Address dropOfAddress) {
        return
                new DropOffAddressJpa(
                        dropOfAddress.street(),
                        dropOfAddress.number(),
                        dropOfAddress.postalCode(),
                        dropOfAddress.city(),
                        dropOfAddress.country()
                );
    }
}



