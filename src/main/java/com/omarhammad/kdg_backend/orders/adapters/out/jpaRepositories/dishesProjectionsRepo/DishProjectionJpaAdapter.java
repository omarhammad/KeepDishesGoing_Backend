package com.omarhammad.kdg_backend.orders.adapters.out.jpaRepositories.dishesProjectionsRepo;

import com.omarhammad.kdg_backend.orders.adapters.out.jpaRepositories.dishesProjectionsRepo.entites.DishProjectionJpaEntity;
import com.omarhammad.kdg_backend.orders.domain.DishProjection;
import com.omarhammad.kdg_backend.orders.domain.Id;
import com.omarhammad.kdg_backend.orders.domain.enums.DishLiveStatus;
import com.omarhammad.kdg_backend.orders.domain.enums.DishStockStatus;
import com.omarhammad.kdg_backend.orders.ports.out.EditDishProjectionPort;
import com.omarhammad.kdg_backend.orders.ports.out.LoadDishProjectionPort;
import com.omarhammad.kdg_backend.orders.ports.out.SaveDishProjectionPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class DishProjectionJpaAdapter implements LoadDishProjectionPort, SaveDishProjectionPort
        , EditDishProjectionPort {

    private final DishProjectionRepository repository;


    @Override
    public Optional<DishProjection> findById(Id<DishProjection> dishProjectionId) {
        Optional<DishProjectionJpaEntity> entity = repository.findById(UUID.fromString(dishProjectionId.value()));
        return entity.map(this::toDishProjection);
    }


    @Override
    public Optional<DishProjection> save(DishProjection dishProjection) {
        return Optional.empty();
    }

    @Override
    public void edit(DishProjection dishProjection) {

    }

    private DishProjection toDishProjection(DishProjectionJpaEntity entity) {
        return new DishProjection(
                new Id<>(entity.getDishId().toString()),
                new Id(entity.getRestaurantId().toString()),
                entity.getOccurredAt(),
                DishLiveStatus.valueOf(entity.getLiveStatus().toUpperCase()),
                DishStockStatus.valueOf(entity.getStockStatus().toUpperCase())
        );
    }

}
