package com.omarhammad.kdg_backend.restaurants.domain;

import com.omarhammad.kdg_backend.restaurants.domain.enums.OrderProjectionStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Setter
@Getter
public class OrderProjection {
    private Id<OrderProjection> orderId;
    private Id<Restaurant> restaurantId;
    private Address dropOfAddress;
    private OrderProjectionStatus status;
    private String rejectedReason;
    private String declinedReason;
    private LocalDateTime occurredAt;


    public OrderProjection() {
    }

    public OrderProjection(Id<OrderProjection> orderId, Id<Restaurant> restaurantId, OrderProjectionStatus status,Address dropOfAddress, LocalDateTime occurredAt) {
        this.orderId = orderId;
        this.restaurantId = restaurantId;
        this.status = status;
        this.dropOfAddress = dropOfAddress;
        this.occurredAt = occurredAt;
    }

}
