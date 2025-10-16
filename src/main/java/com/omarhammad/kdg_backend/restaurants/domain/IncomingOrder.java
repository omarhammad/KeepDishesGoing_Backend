package com.omarhammad.kdg_backend.restaurants.domain;

import com.omarhammad.kdg_backend.restaurants.domain.enums.IncomingOrderStatus;
import lombok.Setter;

import java.time.LocalDateTime;


@Setter
public class IncomingOrder {
    private Id<IncomingOrder> orderId;
    private Id<Restaurant> restaurantId;
    private IncomingOrderStatus status;
    private LocalDateTime occurredAt;


    public IncomingOrder() {
    }

    public IncomingOrder(Id<IncomingOrder> orderId, Id<Restaurant> restaurantId, IncomingOrderStatus status, LocalDateTime occurredAt) {
        this.orderId = orderId;
        this.restaurantId = restaurantId;
        this.status = status;
        this.occurredAt = occurredAt;
    }

    public Id<IncomingOrder> getOrderId() {
        return orderId;
    }

    public Id<Restaurant> getRestaurantId() {
        return restaurantId;
    }

    public LocalDateTime getOccurredAt() {
        return occurredAt;
    }

}
