package com.omarhammad.kdg_backend.orders.ports.in;

import com.omarhammad.kdg_backend.orders.domain.DishProjection;
import com.omarhammad.kdg_backend.orders.domain.Id;
import com.omarhammad.kdg_backend.orders.domain.enums.DishLiveStatus;
import com.omarhammad.kdg_backend.orders.domain.enums.DishStockStatus;

import java.time.LocalDateTime;

public record DishStockStatusProjectorCmd(Id<DishProjection> dishId, Id restaurantId, DishStockStatus newStockStatus,
                                          LocalDateTime occurredAt) {
}
