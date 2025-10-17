package com.omarhammad.kdg_backend.restaurants.adapters.out.jpaRepositories.OrdersProjectionsRespo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Setter
@Table(name = "orders_projection")
public class OrderProjectionJpaEntity {

    @Id
    private UUID orderId;
    private UUID restaurantId;
    private String status;
    private String rejectedReason;
    private String declinedReason;
    private LocalDateTime occurredAt;


    public OrderProjectionJpaEntity(UUID orderId, UUID restaurantId, String status, LocalDateTime occurredAt) {
        this.orderId = orderId;
        this.restaurantId = restaurantId;
        this.status = status;
        this.occurredAt = occurredAt;
    }

    public OrderProjectionJpaEntity() {

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

    public String getDeclinedReason() {
        return declinedReason;
    }

    public String getRejectedReason() {
        return rejectedReason;
    }

    @Override
    public String toString() {
        return "OrderProjectionJpaEntity{" +
                "orderId=" + orderId +
                ", restaurantId=" + restaurantId +
                ", status='" + status + '\'' +
                ", occurredAt=" + occurredAt +
                '}';
    }
}
