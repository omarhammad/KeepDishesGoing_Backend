package com.omarhammad.kdg_backend.restaurants.adapters.out.jpaRepositories.incomingOrdersRespo;

import com.omarhammad.kdg_backend.restaurants.adapters.out.jpaRepositories.incomingOrdersRespo.entities.IncomingOrdersJpaEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IncomingOrdersRepository extends JpaRepository<@NonNull IncomingOrdersJpaEntity, @NonNull UUID> {
}
