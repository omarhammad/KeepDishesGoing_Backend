package com.omarhammad.kdg_backend.orders.adapters.out.jpaRepositories.ordersRepo;

import com.omarhammad.kdg_backend.orders.adapters.out.jpaRepositories.ordersRepo.entites.OrderJpaEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrdersRepository extends JpaRepository<@NotNull OrderJpaEntity, @NotNull UUID> {
}
