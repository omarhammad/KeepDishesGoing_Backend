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

    private Id<DishProjection> dishId;
    private Id restaurantId;
    private LocalDateTime occurredAt;
    private DishLiveStatus liveStatus;
    private DishStockStatus stockStatus;


    public DishProjection(Id<DishProjection> dishId, Id restaurantId, LocalDateTime occurredAt, DishLiveStatus liveStatus) {
        this.dishId = dishId;
        this.restaurantId = restaurantId;
        this.occurredAt = occurredAt;
        this.liveStatus = liveStatus;
    }

    public DishProjection(Id<DishProjection> dishId, Id restaurantId, LocalDateTime occurredAt, DishStockStatus stockStatus) {
        this.dishId = dishId;
        this.restaurantId = restaurantId;
        this.occurredAt = occurredAt;
        this.stockStatus = stockStatus;
    }

    public DishProjection changeLiveStatusTo(DishLiveStatus newLiveStatus) {
        this.liveStatus = newLiveStatus;
        return this;
    }

    public DishProjection changeStockStatusTo(DishStockStatus newStockStatus) {
        this.stockStatus = newStockStatus;
        return this;
    }

    public void at(LocalDateTime occurredAt) {
        this.occurredAt = occurredAt;
    }


}
