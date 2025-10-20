package com.omarhammad.kdg_backend.orders.adapters.out.jpaRepositories.dishesProjectionsRepo;

import com.omarhammad.kdg_backend.orders.domain.DishProjection;
import com.omarhammad.kdg_backend.orders.domain.Id;
import com.omarhammad.kdg_backend.orders.ports.out.EditDishProjectionPort;
import com.omarhammad.kdg_backend.orders.ports.out.LoadDishProjectionPort;
import com.omarhammad.kdg_backend.orders.ports.out.SaveDishProjectionPort;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class DishProjectionJpaAdapter implements LoadDishProjectionPort, SaveDishProjectionPort
        , EditDishProjectionPort {


    @Override
    public Optional<DishProjection> findById(Id<DishProjection> dishProjectionId) {
        return Optional.empty();
    }

    @Override
    public Optional<DishProjection> save(DishProjection dishProjection) {
        return Optional.empty();
    }

    @Override
    public void edit(DishProjection dishProjection) {

    }


}
