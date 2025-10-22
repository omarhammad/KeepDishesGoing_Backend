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

import java.util.Objects;
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
    public DishProjection save(DishProjection dishProjection) {
        DishProjectionJpaEntity entity = toDishProjectionJpaEntity(dishProjection);
        repository.save(entity);
        return toDishProjection(entity);
    }


    @Override
    public void edit(DishProjection dishProjection) {
        DishProjectionJpaEntity entity = toDishProjectionJpaEntity(dishProjection);
        repository.save(entity);
    }

    private DishProjection toDishProjection(DishProjectionJpaEntity entity) {

        String liveStatus = entity.getLiveStatus();
        String stockStatus = entity.getStockStatus();

        return new DishProjection(
                new Id<>(entity.getDishId().toString()),
                new Id(entity.getRestaurantId().toString()),
                entity.getOccurredAt(),
                entity.getPrice(),
                Objects.nonNull(liveStatus) ? DishLiveStatus.valueOf(liveStatus.toUpperCase()) : null,
                Objects.nonNull(stockStatus) ? DishStockStatus.valueOf(stockStatus.toUpperCase()) : null
        );
    }

    private DishProjectionJpaEntity toDishProjectionJpaEntity(DishProjection dishProjection) {
        DishLiveStatus liveStatus = dishProjection.getLiveStatus();
        DishStockStatus stockStatus = dishProjection.getStockStatus();

        return new DishProjectionJpaEntity(
                UUID.fromString(dishProjection.getDishId().value()),
                UUID.fromString(dishProjection.getRestaurantId().value()),
                dishProjection.getOccurredAt(),
                dishProjection.getPrice(),
                Objects.nonNull(liveStatus) ? liveStatus.name() : null,
                Objects.nonNull(stockStatus) ? stockStatus.name() : null
        );
    }

}
