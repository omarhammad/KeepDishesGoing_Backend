package com.omarhammad.kdg_backend.restaurants.adapters.out.jpaRepositories.OrdersProjectionsRespo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders_projection")
public class OrderProjectionJpaEntity {

    @Id
    private UUID orderId;
    private UUID restaurantId;
    private String status;
    private String rejectedReason;
    private String declinedReason;
    private DropOffAddressJpa dropOfAddress;
    private LocalDateTime occurredAt;


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
