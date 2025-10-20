package com.omarhammad.kdg_backend.orders.ports.in;

import com.omarhammad.kdg_backend.orders.domain.DishProjection;
import com.omarhammad.kdg_backend.orders.domain.Id;

import java.util.List;

public record CreateOrderCmd(List<Id<DishProjection>> dishes) {
}
