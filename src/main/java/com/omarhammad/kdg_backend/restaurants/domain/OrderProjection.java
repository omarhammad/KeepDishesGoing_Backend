package com.omarhammad.kdg_backend.restaurants.domain;

import com.omarhammad.kdg_backend.restaurants.domain.enums.OrderProjectionStatus;
import lombok.Setter;

import java.time.LocalDateTime;


@Setter
public class OrderProjection {
    private Id<OrderProjection> orderId;
    private Id<Restaurant> restaurantId;
    private OrderProjectionStatus status;
    private String rejectedReason;
    private String declinedReason;
    private LocalDateTime occurredAt;


    public OrderProjection() {
    }

    public OrderProjection(Id<OrderProjection> orderId, Id<Restaurant> restaurantId, OrderProjectionStatus status, LocalDateTime occurredAt) {
        this.orderId = orderId;
        this.restaurantId = restaurantId;
        this.status = status;
        this.occurredAt = occurredAt;
    }


    public Id<OrderProjection> getOrderId() {
        return orderId;
    }

    public Id<Restaurant> getRestaurantId() {
        return restaurantId;
    }

    public LocalDateTime getOccurredAt() {
        return occurredAt;
    }

    public OrderProjectionStatus getStatus() {
        return status;
    }

    public String getDeclinedReason() {
        return declinedReason;
    }

    public String getRejectedReason() {
        return rejectedReason;
    }
}
