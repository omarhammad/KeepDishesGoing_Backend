package com.omarhammad.kdg_backend.restaurants.adapters.out.jpaRepositories.OrdersProjectionsRespo;

import com.omarhammad.kdg_backend.restaurants.adapters.out.jpaRepositories.OrdersProjectionsRespo.entities.OrderProjectionJpaEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrdersProjectionsRepository extends JpaRepository<@NonNull OrderProjectionJpaEntity, @NonNull UUID> {

    Optional<OrderProjectionJpaEntity> findByOrderIdAndRestaurantId(UUID orderId, UUID restaurantId);
}
