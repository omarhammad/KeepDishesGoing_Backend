package com.omarhammad.kdg_backend.orders.core;

import com.omarhammad.kdg_backend.orders.domain.DishProjection;
import com.omarhammad.kdg_backend.orders.domain.enums.DishStockStatus;
import com.omarhammad.kdg_backend.orders.ports.in.DishLiveStatusProjector;
import com.omarhammad.kdg_backend.orders.ports.in.DishLiveStatusProjectorCmd;
import com.omarhammad.kdg_backend.orders.ports.out.EditDishProjectionPort;
import com.omarhammad.kdg_backend.orders.ports.out.LoadDishProjectionPort;
import com.omarhammad.kdg_backend.orders.ports.out.SaveDishProjectionPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class DefaultDishLiveStatusProjector implements DishLiveStatusProjector {


    private final LoadDishProjectionPort loadDishProjectionPort;
    private final SaveDishProjectionPort saveDishProjectionPort;
    private final EditDishProjectionPort editDishProjectionPort;


    @Override
    public void project(DishLiveStatusProjectorCmd cmd) {

        DishProjection dishProjection = loadDishProjectionPort.findById(cmd.dishId())
                .orElse(null);

        if (Objects.nonNull(dishProjection)) {
            dishProjection.changeLiveStatusTo(cmd.newLiveStatus()).withPrice(cmd.dishPrice()).at(cmd.occurredAt());
            editDishProjectionPort.edit(dishProjection);
            return;
        }

        dishProjection = new DishProjection(
                cmd.dishId(),
                cmd.restaurantId(),
                cmd.occurredAt(),
                cmd.dishPrice(),
                cmd.newLiveStatus(),
                DishStockStatus.OUT_OF_STOCK
        );

        saveDishProjectionPort.save(dishProjection);

    }
}
