package com.omarhammad.kdg_backend.orders.adapters.out.jpaRepositories.dishesProjectionsRepo;

import com.omarhammad.kdg_backend.orders.adapters.out.jpaRepositories.dishesProjectionsRepo.entites.DishProjectionJpaEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DishProjectionRepository extends JpaRepository<@NotNull DishProjectionJpaEntity, @NotNull UUID> {
}
