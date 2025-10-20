package com.omarhammad.kdg_backend.orders.adapters.out.jpaRepositories.dishesProjectionsRepo.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "dish_projection")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DishProjectionJpaEntity {


    @Id
    private UUID dishId;
    private UUID restaurantId;
    private LocalDateTime occurredAt;
    private String liveStatus;
    private String stockStatus;

    public DishProjectionJpaEntity(UUID restaurantId, LocalDateTime occurredAt, String liveStatus, String stockStatus) {
        this.restaurantId = restaurantId;
        this.occurredAt = occurredAt;
        this.liveStatus = liveStatus;
        this.stockStatus = stockStatus;
    }
}
