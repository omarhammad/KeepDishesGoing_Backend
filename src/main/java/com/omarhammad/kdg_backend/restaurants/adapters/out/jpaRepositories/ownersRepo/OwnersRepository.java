package com.omarhammad.kdg_backend.restaurants.adapters.out.jpaRepositories.ownersRepo;

import com.omarhammad.kdg_backend.restaurants.adapters.out.jpaRepositories.ownersRepo.entites.OwnerJpaEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OwnersRepository extends JpaRepository<@NonNull OwnerJpaEntity, @NonNull UUID> {
}
