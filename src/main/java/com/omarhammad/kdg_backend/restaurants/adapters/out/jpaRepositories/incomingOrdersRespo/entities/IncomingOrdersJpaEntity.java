package com.omarhammad.kdg_backend.restaurants.adapters.out.jpaRepositories.incomingOrdersRespo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Setter
@Table(name = "incoming_orders_projection")
public class IncomingOrdersJpaEntity {
    @Id
    private UUID orderId;
    private UUID restaurantId;
    private String status;
    private LocalDateTime occurredAt;


    public IncomingOrdersJpaEntity(UUID orderId, UUID restaurantId, LocalDateTime occurredAt) {
        this.orderId = orderId;
        this.restaurantId = restaurantId;
        this.occurredAt = occurredAt;
    }

    public IncomingOrdersJpaEntity() {

    }

    public UUID getOrderId() {
        return orderId;
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getOccurredAt() {
        return occurredAt;
    }
}
