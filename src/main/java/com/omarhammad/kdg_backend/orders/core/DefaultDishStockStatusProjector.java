package com.omarhammad.kdg_backend.orders.core;

import com.omarhammad.kdg_backend.orders.domain.DishProjection;
import com.omarhammad.kdg_backend.orders.domain.enums.DishLiveStatus;
import com.omarhammad.kdg_backend.orders.ports.in.DishStockStatusProjector;
import com.omarhammad.kdg_backend.orders.ports.in.DishStockStatusProjectorCmd;
import com.omarhammad.kdg_backend.orders.ports.out.EditDishProjectionPort;
import com.omarhammad.kdg_backend.orders.ports.out.LoadDishProjectionPort;
import com.omarhammad.kdg_backend.orders.ports.out.SaveDishProjectionPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class DefaultDishStockStatusProjector implements DishStockStatusProjector {


    private final LoadDishProjectionPort loadDishProjectionPort;
    private final SaveDishProjectionPort saveDishProjectionPort;
    private final EditDishProjectionPort editDishProjectionPort;

    @Override
    public void project(DishStockStatusProjectorCmd cmd) {

        DishProjection dishProjection = loadDishProjectionPort.findById(cmd.dishId())
                .orElse(null);


        if (Objects.nonNull(dishProjection)) {
            dishProjection.changeStockStatusTo(cmd.newStockStatus()).at(cmd.occurredAt());
            editDishProjectionPort.edit(dishProjection);
            return;
        }


        dishProjection = new DishProjection(
                cmd.dishId(),
                cmd.restaurantId(),
                cmd.occurredAt(),
                DishLiveStatus.UNPUBLISHED,
                cmd.newStockStatus()
        );

        saveDishProjectionPort.save(dishProjection);


    }
}
