package com.omarhammad.kdg_backend.restaurants.core;

import com.omarhammad.kdg_backend.restaurants.domain.OrderProjection;
import com.omarhammad.kdg_backend.restaurants.domain.enums.OrderProjectionStatus;
import com.omarhammad.kdg_backend.restaurants.ports.in.OrderDeliveredProjector;
import com.omarhammad.kdg_backend.restaurants.ports.in.OrderDeliveredProjectorCmd;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadOrderProjection;
import com.omarhammad.kdg_backend.restaurants.ports.out.UpdateOrderProjection;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class DefaultOrderDeliveredProjector implements OrderDeliveredProjector {


    private LoadOrderProjection loadOrderProjection;
    private UpdateOrderProjection updateOrderProjection;


    @Override
    public void project(OrderDeliveredProjectorCmd cmd) {
        OrderProjection orderProjection = loadOrderProjection.findOrderProjectionByIdAndRestaurantId(cmd.orderId(), cmd.restaurantId())
                .orElse(null);


        if (Objects.isNull(orderProjection)) return;

        orderProjection.setStatus(OrderProjectionStatus.DELIVERED);
        orderProjection.setOccurredAt(cmd.occurredAt());
        updateOrderProjection.update(orderProjection);

    }
}
