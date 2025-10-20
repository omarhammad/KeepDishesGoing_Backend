package com.omarhammad.kdg_backend.orders.domain;

import com.omarhammad.kdg_backend.orders.domain.enums.DishLiveStatus;
import com.omarhammad.kdg_backend.orders.domain.enums.DishStockStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DishProjection {

    private String dishId;
    private String restaurantId;
    private LocalDateTime occurredAt;
    private DishLiveStatus liveStatus;
    private DishStockStatus stockStatus;
}
