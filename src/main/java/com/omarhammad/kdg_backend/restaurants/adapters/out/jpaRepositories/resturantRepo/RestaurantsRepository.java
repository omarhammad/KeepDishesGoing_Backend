package com.omarhammad.kdg_backend.restaurants.adapters.out.jpaRepositories.resturantRepo;

import com.omarhammad.kdg_backend.restaurants.adapters.out.jpaRepositories.resturantRepo.entities.RestaurantsJpaEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RestaurantsRepository extends JpaRepository<@NonNull RestaurantsJpaEntity, @NonNull UUID> {


    Optional<RestaurantsJpaEntity> findRestaurantsJpaEntitiesByOwner(UUID owner);
}
