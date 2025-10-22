package com.omarhammad.kdg_backend.orders.ports.in;

import com.omarhammad.kdg_backend.orders.domain.DishProjection;
import com.omarhammad.kdg_backend.orders.domain.Id;
import com.omarhammad.kdg_backend.orders.domain.enums.DishLiveStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DishLiveStatusProjectorCmd(Id<DishProjection> dishId, Id restaurantId, BigDecimal dishPrice,
                                         DishLiveStatus newLiveStatus,
                                         LocalDateTime occurredAt) {
}
