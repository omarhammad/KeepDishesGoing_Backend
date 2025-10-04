package com.omarhammad.kdg_backend.restaurants.adapters.out.jpaRepositories.dishesRepo;

import com.omarhammad.kdg_backend.restaurants.adapters.out.jpaRepositories.dishesRepo.entites.DishJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

public interface DishesRepository extends JpaRepository<@NonNull DishJpaEntity, @NonNull UUID> {

    List<DishJpaEntity> findAllByRestaurant(UUID restaurant);
}
